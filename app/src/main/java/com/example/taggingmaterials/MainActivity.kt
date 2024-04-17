package com.example.taggingmaterials

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.taggingmaterials.data.TaggedImage
import com.example.taggingmaterials.screen.MainScreen
import com.example.taggingmaterials.service.OverlayService
import com.example.taggingmaterials.ui.theme.TaggingMaterialsTheme
import com.example.taggingmaterials.viewmodel.TaggingMaterialViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val taggingMaterialViewModel: TaggingMaterialViewModel by viewModels()
    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            // Callback is invoked after the user selects a media item or closes the
            // photo picker.
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")
                val takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                contentResolver.takePersistableUriPermission(uri, takeFlags)
                taggingMaterialViewModel.inputImageUri = uri.toString()
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }

    private val requestImagePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                pickMedia.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.SingleMimeType("image/*")
                    )
                )
            } else {
                //TODO : 許可するように誘導するテキストの表示にする
                val context: Context = this
                Toast.makeText(context, R.string.app_name, Toast.LENGTH_SHORT).show()
            }
        }


    private fun checkImagePermissions() {
        val context: Context = applicationContext
        val permission = android.Manifest.permission.READ_MEDIA_IMAGES
        if (ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            pickMedia.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.SingleMimeType("image/*")
                )
            )
        } else {
            requestImagePermissionLauncher.launch(permission)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startService(Intent(this, OverlayService::class.java))

        setContent {
            TaggingMaterialsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    Box {
                        MainScreen(taggingMaterialViewModel)
                        CheckPermissionDialog()

                        //ここのボタンの機能をアプリをスリープにしている状態でもフローティングボタンで使用できるようにしたい
                        FloatingActionButton(
                            onClick = {
                                checkImagePermissions()
                            },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Floating action button."
                            )
                        }
                    }
                }
            }
        }
    }

}


@Composable
private fun CheckPermissionDialog(
    taggingMaterialViewModel: TaggingMaterialViewModel = viewModel()
) {
    if (taggingMaterialViewModel.canGetUri()) {
        var textState by remember { mutableStateOf(TextFieldValue("")) }
        Dialog(
            onDismissRequest = { taggingMaterialViewModel.inputImageUri = "" },
        ) {
            Card(
                shape = RoundedCornerShape(8.dp)
            ) {
                LazyColumn() {
                    item {
                        Box(
                        ) {
                            AsyncImage(
                                model = taggingMaterialViewModel.inputImageUri,
                                contentDescription = null
                            )
                        }
                    }
                    item(key = "TextField") {
                        Row {
                            TextField(
                                value = textState,
                                onValueChange = { newValue ->
                                    textState = newValue.copy(
                                        selection = TextRange(newValue.text.length)
                                    )
                                }
                            )
                            Button(onClick = {
                                taggingMaterialViewModel.insertTaggedImage(
                                    TaggedImage(
                                        timestamp = Date().time,
                                        imageUri = taggingMaterialViewModel.inputImageUri,
                                        tag1 = textState.text
                                    )
                                )
                                taggingMaterialViewModel.inputImageUri = ""

                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Floating action button."
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


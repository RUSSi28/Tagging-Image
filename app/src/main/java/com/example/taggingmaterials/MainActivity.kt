package com.example.taggingmaterials

import android.os.Bundle
import android.util.Log
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.taggingmaterials.data.TaggedImage
import com.example.taggingmaterials.screen.MainScreen
import com.example.taggingmaterials.ui.theme.TaggingMaterialsTheme
import com.example.taggingmaterials.viewmodel.TaggingMaterialViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val taggingMaterialViewModel: TaggingMaterialViewModel by viewModels()
    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            taggingMaterialViewModel.inputImageUri = uri.toString()
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaggingMaterialsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    val coroutineScope = rememberCoroutineScope()
                    ContextCompat.startForegroundService(this, intent)
                    // or
                    // startService(intent)           // for android version lower than 8.0 (android O)
                    // startForegroundService(intent) // for android 8.0 and higher
                    Box {
                        MainScreen(taggingMaterialViewModel)
                        if (taggingMaterialViewModel.canGetUri()) {
                            var tag by remember{ mutableStateOf("") }
                            AlertDialog(
                                onDismissRequest = { taggingMaterialViewModel.inputImageUri = "" },
                            ) {
                                Card (
                                    shape = RoundedCornerShape(8.dp)
                                ){
                                    LazyColumn() {
                                        item {
                                            Box(
                                            ) {
                                                AsyncImage(
                                                    model = taggingMaterialViewModel.inputImageUri,
                                                    contentDescription = null
                                                )
                                                TextButton(
                                                    onClick = { /*TODO*/ },
                                                    modifier = Modifier.align(Alignment.BottomEnd)
                                                ) {
                                                    Text(text = "add")
                                                }
                                            }
                                        }
                                        item (key = "TextField"){
                                            Row {
                                                TextField(
                                                    value = tag,
                                                    onValueChange = { tag = it }
                                                )
                                                Button(onClick = {
                                                    Log.d("add", "add")
                                                    taggingMaterialViewModel.inputImageUri = ""
                                                    coroutineScope.launch {
                                                        withContext(Dispatchers.Default) {
                                                            taggingMaterialViewModel.insertTaggedImage(
                                                                TaggedImage(
                                                                    imageUri = taggingMaterialViewModel.inputImageUri,
                                                                    tag1 = tag
                                                                )
                                                            )
                                                        }
                                                    }
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

                        //ここのボタンの機能をアプリをスリープにしている状態でもフローティングボタンで使用できるようにしたい
                        FloatingActionButton(
                            onClick = {
                                pickMedia.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.SingleMimeType("image/*")
                                    )
                                )
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


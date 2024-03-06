package com.example.taggingmaterials.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.taggingmaterials.R
import com.example.taggingmaterials.data.TaggedImage
import com.example.taggingmaterials.viewmodel.TaggingMaterialViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
//    TODO : 検索語についてのナビゲーショングラフのさくせい
    taggingMaterialViewModel: TaggingMaterialViewModel,
    modifier: Modifier = Modifier
) {
    Column {
        SearchBar(
            leadingIcon = {
                Image(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(id = R.string.search_text_field),
                    modifier = modifier.size(32.dp)
                )
            },
            //TODO: クエリした文字をテキストボタンにしてタップしたらqueryの文字列をそれに入れ替えるようにする
            query = taggingMaterialViewModel.getInText(),
            onQueryChange = {
                taggingMaterialViewModel.changeInputText(it)

                            },
            placeholder = {
                          if (taggingMaterialViewModel.getInText() == "") {
                              Text(
                                  text = "Recently Used",
                                  color = Color.Gray
                              )
                          }
            },
            //Daoを用いて検索ワードからSQLクエリ→画像一覧を表示
            onSearch = {},
            active = taggingMaterialViewModel.getIsSearchBarActive(),
            onActiveChange = { taggingMaterialViewModel.changeIsSearchBarActive(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            //Contentは検索結果に応じた単語を含むタグ(それをもつ画像数も)をリスト形式で表示
            //TODO: 文字列入れるたびのクエリの更新ができていない
            Column() {
                val tags = taggingMaterialViewModel.queryTags.collectAsState().value
                for(tag in tags){
                    TextButton(onClick = {
                        taggingMaterialViewModel.changeInputText(tag)
                    }) {
                        Text(text = tag)
                    }
                }
            }
        }
        val images = taggingMaterialViewModel.currentlyUsedImages.collectAsState().value.toPersistentList()
        Log.d("Get ImageList", "MainScreen: $images")
        CurrentlyUsedImageGrid(
            taggingMaterialViewModel = taggingMaterialViewModel
        )
    }
}

@Composable
fun CurrentlyUsedImageGrid(taggingMaterialViewModel: TaggingMaterialViewModel) {
    val coroutineScope = rememberCoroutineScope()
    if (taggingMaterialViewModel.currentlyUsedImages.collectAsState().value.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(
                items = taggingMaterialViewModel.currentlyUsedImages.value,
                key = { item -> item.id }
            ) {
                AsyncImage(
                    model = it.imageUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    placeholder = ColorPainter(color = Color.LightGray),
                    error = ColorPainter(color = Color.Black),
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clickable(
                            onClick = {
                                coroutineScope.launch {
                                    taggingMaterialViewModel.deleteTaggedImage(it)
                                }
                            }
                        )
                            //TODO : NavigateでImageDetailコンポーザブルに遷移する
                            //一度deleteにしておく

                )
                Log.d("AsyncImage", "CurrentlyUsedImageGrid: ${it.imageUri} ")
            }
        }
    } else {
        //TODO : emptyListの時にはローディングするようにする
    }
}


@Composable
fun ImageDetail(taggedImage: TaggedImage) {
    Column {
        AsyncImage(
            model = taggedImage.imageUri,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
        )
    }
}
package com.example.taggingmaterials.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.example.taggingmaterials.R
import com.example.taggingmaterials.data.TaggedImage
import com.example.taggingmaterials.viewmodel.TaggingMaterialViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    taggingMaterialViewModel: TaggingMaterialViewModel, modifier: Modifier = Modifier
) {

    val coroutineScope = rememberCoroutineScope()
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
            query = taggingMaterialViewModel.inputText,
            onQueryChange = {
                taggingMaterialViewModel.inputText = it
                coroutineScope.launch(Dispatchers.Default) {
                    taggingMaterialViewModel.getQueryTags()
                }
            },
            placeholder = {
                if (taggingMaterialViewModel.inputText == "") {
                    Text(
                        text = "Recently Used", color = Color.Gray
                    )
                }
            },
            //Daoを用いて検索ワードからSQLクエリ→画像一覧を表示
            onSearch = {
                //taggingViewModel.currentlyUsedImageの値をクエリの結果に置き換える
                //Composableが使えないのでどうしよう
                coroutineScope.launch {
                    taggingMaterialViewModel.getSearchedImages()
                    taggingMaterialViewModel.changeIsSearchBarActive(false)
                }
            },
            active = taggingMaterialViewModel.getIsSearchBarActive(),
            onActiveChange = { taggingMaterialViewModel.changeIsSearchBarActive(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            if (taggingMaterialViewModel.inputText == "") {
                Column() {
                    for (tag in taggingMaterialViewModel.allTags.collectAsState(initial = emptyList()).value.toPersistentList()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = { taggingMaterialViewModel.inputText = tag }
                                )
                                .padding(8.dp)
                        ) {
                            Text(text = tag)
                        }
                    }
                }
            } else {
                Column() {
                    for (tag in taggingMaterialViewModel.queryTags) {
                        TextButton(onClick = {
                            taggingMaterialViewModel.inputText = tag
                        }) {
                            Text(text = tag)
                        }
                    }
                }
            }
        }

        if (taggingMaterialViewModel.inputText == "") {
            ImageGrid(
                images = taggingMaterialViewModel.currentlyUsedImages.collectAsLazyPagingItems(),
                taggingMaterialViewModel = taggingMaterialViewModel
            )
        } else {
            ImageGrid(
                images = taggingMaterialViewModel.currentlyUsedImages.collectAsLazyPagingItems(),
                taggingMaterialViewModel = taggingMaterialViewModel
            )
        }
    }
}

@Composable
fun ImageGrid(
    images: LazyPagingItems<TaggedImage>, taggingMaterialViewModel: TaggingMaterialViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    if (images.itemCount != 0) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(count = images.itemCount, key = images.itemKey()) {
                Log.d("MainScreen", "ImageGrid: ${images[it]}")
                //毎回nullでページングが入ってきてるのなぜなぜ
                if (images[it] != null) {

                    AsyncImage(
                        model = images[it]?.imageUri,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        placeholder = ColorPainter(color = Color.LightGray),
                        error = ColorPainter(color = Color.Black),
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clickable(onClick = {
                                coroutineScope.launch {
                                    taggingMaterialViewModel.deleteTaggedImage(images[it]!!)
                                }
                            })
                        //TODO : NavigateでImageDetailコンポーザブルに遷移する
                        //一度deleteにしておく

                    )
                }
                Log.d("AsyncImage", "CurrentlyUsedImageGrid: ${images[it]?.imageUri} ")
            }
        }
    } else {
        //TODO : emptyListの時にはローディングするようにする
    }
}


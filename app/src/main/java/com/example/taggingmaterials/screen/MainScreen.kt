package com.example.taggingmaterials.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import coil.compose.AsyncImage
import com.example.taggingmaterials.R
import com.example.taggingmaterials.data.TaggedImage
import com.example.taggingmaterials.viewmodel.TaggingMaterialViewModel
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            query = taggingMaterialViewModel.getInText(),
            onQueryChange = {
                taggingMaterialViewModel.changeInputText(it)
                coroutineScope.launch(Dispatchers.Default) {
                    taggingMaterialViewModel.getQueryTags()
                }
            },
            placeholder = {
                if (taggingMaterialViewModel.getInText() == "") {
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
                    withContext(Dispatchers.Default) {
                        taggingMaterialViewModel.getSearchedImages()
                        taggingMaterialViewModel.changeIsSearchBarActive(false)
                    }
                }
            },
            active = taggingMaterialViewModel.getIsSearchBarActive(),
            onActiveChange = { taggingMaterialViewModel.changeIsSearchBarActive(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            //Contentは検索結果に応じた単語を含むタグ(それをもつ画像数も)をリスト形式で表示
            //入力文字がないときは全てのタグを表示
            //入力文字があるときはクエリ結果のタグを表示
            if (taggingMaterialViewModel.getInText() == "") {
                Column() {
                    for (tag in taggingMaterialViewModel.allTags.collectAsState(initial = emptyList()).value.toPersistentList()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = { taggingMaterialViewModel.changeInputText(tag) }
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
                            taggingMaterialViewModel.changeInputText(tag)
                        }) {
                            Text(text = tag)
                        }
                    }
                }
            }
        }

        //入力文字がないときは最近使った画像を表示
        //入力文字があるときはクエリ結果の画像を表示
        if (taggingMaterialViewModel.getInText() == "") {
            ImageGrid(
                images = taggingMaterialViewModel.currentlyUsedImages.collectAsState(initial = emptyList()).value.toPersistentList(),
                taggingMaterialViewModel = taggingMaterialViewModel
            )
        } else {
            ImageGrid(
                images = taggingMaterialViewModel.searchedImages.toPersistentList(),
                taggingMaterialViewModel = taggingMaterialViewModel
            )
        }
    }
}

@Composable
fun ImageGrid(
    images: PersistentList<TaggedImage>, taggingMaterialViewModel: TaggingMaterialViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    if (images.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(items = images, key = { item -> item.id }) {
                AsyncImage(
                    model = it.imageUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    placeholder = ColorPainter(color = Color.LightGray),
                    error = ColorPainter(color = Color.Black),
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clickable(onClick = {
                            coroutineScope.launch {
                                taggingMaterialViewModel.deleteTaggedImage(it)
                            }
                        })
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
            modifier = Modifier.fillMaxSize()
        )
    }
}
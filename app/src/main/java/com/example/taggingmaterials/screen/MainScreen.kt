package com.example.taggingmaterials.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.taggingmaterials.R
import com.example.taggingmaterials.data.TaggedImage
import com.example.taggingmaterials.viewmodel.TaggingMaterialViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList

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
            query = taggingMaterialViewModel.getInText(),
            onQueryChange = { taggingMaterialViewModel.changeInputText(it) }, 
            placeholder = {
                          if (taggingMaterialViewModel.getInText() == "") {
                              Text(
                                  text = "Recently Used",
                                  color = Color.Gray
                              )
                          }
            },
            //Daoを用いて検索ワードからSQLクエリ
            onSearch = {},
            active = taggingMaterialViewModel.getIsSearchBarActive(),
            onActiveChange = { taggingMaterialViewModel.changeIsSearchBarActive(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            //Contentは検索結果に応じた単語を含むタグ(それをもつ画像数も)をリスト形式で表示

        }
        CurrentlyUsedImageGrid(
            currentlyUsedImages = taggingMaterialViewModel.currentlyUsedImages.collectAsState().value.toPersistentList()
        )
    }
}

@Composable
fun CurrentlyUsedImageGrid(currentlyUsedImages: ImmutableList<TaggedImage>) {
    if (currentlyUsedImages.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2)
        ) {
            items(
                items = currentlyUsedImages,
                key = { item -> item.id }
            ) {
                AsyncImage(
                    model = it.imageUri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .aspectRatio(1f)
                        .clickable(
                            onClick = {

                            }
                        )
                            //TODO : NavigateでImageDetailコンポーザブルに遷移する
                            //一度deleteにしておく

                )
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
package com.example.taggingmaterials.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.taggingmaterials.R
import com.example.taggingmaterials.viewmodel.TaggingMaterialViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    taggingMaterialViewModel: TaggingMaterialViewModel,
    modifier: Modifier = Modifier
) {
    var inputText by remember { mutableStateOf("") }
    var bool by remember { mutableStateOf(false) }

    Row (
        horizontalArrangement = Arrangement.Center
    ){
        SearchBar(
            leadingIcon = {
                Image(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(id = R.string.search_text_field),
                    modifier = modifier.size(32.dp)
                )
            },
            query = inputText,
            onQueryChange = {inputText = it},
            //Daoを用いて検索ワードからSQLクエリ
            onSearch = {},
            active = bool,
            onActiveChange = {bool = it}
        ) {
            //Contentは検索結果に応じた単語を含むタグがつけられたイラストをグリッドで表示
            Text(text = "Test")
        }
    }
}


package com.learn.jetmovie.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.learn.jetmovie.R
import com.learn.jetmovie.ui.theme.JetMovieTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarProses(
    query : String,
    onQueryChange : (String) -> Unit,
    modifier: Modifier = Modifier
){
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        },
        placeholder = {
            Text(stringResource(R.string.search_movie))
        },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)
    ){

    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarProsesPreview(){
    JetMovieTheme {
        SearchBarProses(
            query = "" ,
            onQueryChange = {}
        )
    }
}
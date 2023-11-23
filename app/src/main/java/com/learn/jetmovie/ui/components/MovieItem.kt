package com.learn.jetmovie.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.learn.jetmovie.R
import com.learn.jetmovie.ui.theme.JetMovieTheme
import com.learn.jetmovie.ui.theme.Shapes

@Composable
fun MovieItem(
    title : String,
    photoUrl : String,
    price : Int,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
    ) {
        AsyncImage(
            model =photoUrl ,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(240.dp)
                .height(450.dp)
                .clip(Shapes.medium)

        )
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
        Text(
            text = stringResource(R.string.required_point,price ),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.secondary

        )
    }
}

//preview movie item untuk list
@Composable
@Preview(showBackground = true)
fun MovieItemPreview(){
    JetMovieTheme {
        MovieItem("slebeww","\"https://simfonifm.com/wp-content/uploads/2022/08/poster-pengabdi-setan-2-communion.jpeg\\n\"",50000)
    }
}
package com.learn.jetmovie.ui.Screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.learn.jetmovie.R
import com.learn.jetmovie.ui.theme.JetMovieTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
   Column(modifier = modifier) {
       CenterAlignedTopAppBar(
           title = {
               Text(
                   text = stringResource(R.string.Profile_text) ,
                   fontWeight = FontWeight.Bold,
                   fontSize = 18.sp,
                   textAlign = TextAlign.Center,
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(horizontal = 12.dp)
               )
           }
       )
       //photo profile
       Column(
           modifier = Modifier
               .verticalScroll(rememberScrollState())
               .weight(1f),
           horizontalAlignment = Alignment.CenterHorizontally
       ) {
           Box { //image
               Image(
                   painter = painterResource(id = R.drawable.oby),
                   contentDescription = "poto ku hehe",
                   contentScale = ContentScale.Crop,
                   alignment = Alignment.Center,
                   modifier = Modifier
                       .size(280.dp)
                       .fillMaxWidth()
                       .padding(5.dp)
                       .clip(CircleShape)
               )
           }

           //name and email
           Column(
               horizontalAlignment = Alignment.Start,
               modifier = Modifier.padding(16.dp)
           ) {
               Text(
                   text = stringResource(R.string.name_text),
                   style = MaterialTheme.typography.titleMedium.copy(
                       fontWeight = FontWeight.Medium
                   ),
                   modifier = modifier
                       .padding(vertical = 10.dp)
               )
               Text(
                   text = stringResource(id = R.string.bobby_saputra),
                   textAlign = TextAlign.Center,
                   style = MaterialTheme.typography.headlineLarge.copy(
                       fontWeight = FontWeight.ExtraBold,
                       fontSize = 22.sp,
                   ),
               )
               Spacer(
                   modifier = Modifier
                       .fillMaxWidth()
                       .height(16.dp)
               )

               //email
               Text(
                   text = stringResource(R.string.email_text),
                   style = MaterialTheme.typography.titleMedium.copy(
                       fontWeight = FontWeight.Medium
                   ),
                   modifier = modifier
                       .padding(vertical = 10.dp)
               )
               Text(
                   text = stringResource(id = R.string.bobby_email),
                   textAlign = TextAlign.Center,
                   style = MaterialTheme.typography.displayMedium.copy(
                       fontWeight = FontWeight.ExtraBold,
                       fontSize = 22.sp,
                   ),
               )
           }
       }
   }
}

//preview
@Preview(showBackground = true, device = Devices.NEXUS_6P)
@Composable
fun DetailContentPreview(){
    JetMovieTheme {
        ProfileScreen(
        )
    }
}
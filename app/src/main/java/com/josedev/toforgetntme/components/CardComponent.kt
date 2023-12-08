package com.josedev.toforgetntme.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CardComponent() {
    Card (
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ){
        Row (

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(10.dp)
        ){
            Column {
                Text(
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    text = "Task Title",
                    modifier = Modifier. padding(15.dp))
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    modifier = Modifier.padding(5.dp),
                    textAlign = TextAlign.Justify,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.ExtraLight,
                    text = "Random descrip that usually it is not as long as this")
            }
            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
        }
    }
}
@Preview
@Composable
fun CardPreview() {
    CardComponent()
}
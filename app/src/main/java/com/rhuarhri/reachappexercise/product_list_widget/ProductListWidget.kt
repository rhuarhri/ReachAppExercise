package com.rhuarhri.reachappexercise.product_list_widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.rhuarhri.reachappexercise.ProductItem

class ProductListWidget {

    @Composable
    fun ProductList(productList : List<ProductItem>, itemPressed : (item : ProductItem) -> Unit) {
        LazyColumn(Modifier.fillMaxSize()) {
            items(items = productList) {item ->
                ListItem(item = item, itemPressed)
            }
        }
    }

    @Composable
    private fun ListItem(item: ProductItem, itemPressed : (item : ProductItem) -> Unit) {

        Column(
            Modifier
                .fillMaxWidth()
                .height(250.dp).clickable {
                    itemPressed.invoke(item)
                }) {

            Row(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                Arrangement.Center
            ) {
                Image(painter = rememberImagePainter(item.imageURL), contentDescription = null,
                    Modifier.fillMaxSize())
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                Arrangement.Center) {
                Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(item.name, fontWeight = FontWeight.Bold)
                    Text("$" + item.price.toString())
                }
            }
        }

    }

}
package com.rhuarhri.reachappexercise.product_description_widget

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.rhuarhri.reachappexercise.ProductItem

class ProductDescriptionWidget {

    @Composable
    fun Widget(item : ProductItem, visible : Boolean, close : () -> Unit) {

        if (visible == true) {
            Box(modifier = Modifier.fillMaxSize(), Alignment.BottomCenter) {
            Card(
                backgroundColor = Color.White,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                ) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        Text(item.name, fontWeight = FontWeight.Bold, fontSize = 25.sp)
                        Button(onClick = {close.invoke()},) {
                            Text("close")
                        }
                    }

                    Text(item.description, modifier = Modifier.padding(15.dp))

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        Text("$" + item.price, fontWeight = FontWeight.Bold, fontSize = 25.sp)
                        Button(onClick = {close.invoke()},) {
                            Text("Buy")
                        }
                    }
                }
            }
        }
        }
    }
}

class ProductDescriptionWidgetViewModel : ViewModel() {

    var visible by mutableStateOf(value = false)
    var selectedItem by mutableStateOf(value = ProductItem("", "", "", 0.00))

    fun setSelectedProduct(product : ProductItem) {
        visible = true
        selectedItem = product
    }

    fun hide() {
        visible = false
    }

}
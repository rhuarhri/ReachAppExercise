package com.rhuarhri.reachappexercise

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.rhuarhri.reachappexercise.product_description_widget.ProductDescriptionWidget
import com.rhuarhri.reachappexercise.product_description_widget.ProductDescriptionWidgetViewModel
import com.rhuarhri.reachappexercise.product_list_widget.ProductListWidget
import com.rhuarhri.reachappexercise.ui.theme.ReachAppExerciseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel : MainActivityViewModel by viewModels()
        val productDescriptionWidgetViewModel : ProductDescriptionWidgetViewModel by viewModels()

        setContent {
            ReachAppExerciseTheme {
                
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Reach products",
                                )
                            },
                        )
                    },
                    content = {

                            val productList by viewModel.productListState.observeAsState(initial = listOf())
                            ProductListWidget().ProductList(productList, { productItem ->
                                productDescriptionWidgetViewModel.setSelectedProduct(productItem)
                            })

                            ProductDescriptionWidget().Widget(
                                item = productDescriptionWidgetViewModel.selectedItem,
                                visible = productDescriptionWidgetViewModel.visible,
                                close = {
                                    productDescriptionWidgetViewModel.hide()
                                }
                            )

                    }
                )

            }
        }
    }
}
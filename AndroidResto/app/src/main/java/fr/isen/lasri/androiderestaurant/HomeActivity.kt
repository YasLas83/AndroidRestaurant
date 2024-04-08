package fr.isen.lasri.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.isen.lasri.androiderestaurant.ui.theme.AndroidERestaurantTheme

enum class MenuType {
    STARTER,
    MAIN,
    DESSERT;

    @Composable
    fun title(): String {
        return when (this) {
            STARTER -> stringResource(id = R.string.menu_entree)
            MAIN -> stringResource(id = R.string.menu_plat)
            DESSERT -> stringResource(id = R.string.menu_dessert)
        }
    }
}

interface MenuInterface {
    fun displayMenu(dishType: MenuType)
}

class HomeActivity : ComponentActivity(), MenuInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidERestaurantTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetupView(this)
                }
            }
        }
        Log.d("lifeCycle", "Activité Principale - OnCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("lifeCycle", "Activité Principale - onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("lifeCycle", "Activité Principale - onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("lifeCycle", "Activité Principale - onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("lifeCycle", "Activité Principale - onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("lifeCycle", "Activité Principale - onDestroy")
    }

    override fun displayMenu(dishType: MenuType) {
        val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra(MenuActivity.CATEGROY_EXTRA_KEY, dishType)
        startActivity(intent)
    }
}

@Composable
fun SetupView(menu: MenuInterface) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_resto),
                contentDescription = stringResource(id = R.string.logo_description),
                modifier = Modifier.size(80.dp)
            )
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = stringResource(id = R.string.shopping_cart_description),
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        val intentPanier = Intent(context, PanierActivity::class.java)
                        context.startActivity(intentPanier)
                    }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomButton(type = MenuType.STARTER, menu)
            CustomButton(type = MenuType.MAIN, menu)
            CustomButton(type = MenuType.DESSERT, menu)
        }
    }
}

@Composable
fun CustomButton(type: MenuType, menu: MenuInterface) {
    Button(
        onClick = { menu.displayMenu(type) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(60.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = type.title(),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidERestaurantTheme {
        SetupView(HomeActivity())
    }
}


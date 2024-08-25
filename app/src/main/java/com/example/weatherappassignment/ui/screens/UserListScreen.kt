package com.example.weatherappassignment.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.weatherappassignment.UserViewModel
import com.example.weatherappassignment.data.User
import com.example.weatherappassignment.navigation.WeatherScreens
import kotlinx.coroutines.launch
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import com.example.weatherappassignment.ui.theme.GradientBrush


@Composable
fun UserListScreen(navController: NavHostController, viewModel: UserViewModel = viewModel()) {
    val users by viewModel.allUsers.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User List") },
                actions = {
                    IconButton(onClick = { navController.navigate(WeatherScreens.UserForm.route) }) {
                        Icon(Icons.Default.Add, contentDescription = "Add User")
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn(modifier = Modifier.padding(paddingValues)) {
                    items(users) { user ->
                        UserListItem(
                            user = user,
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserListItem(user: User, navController: NavHostController, viewModel: UserViewModel) {
    val coroutineScope = rememberCoroutineScope()

    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToEnd || it == DismissValue.DismissedToStart) {
                coroutineScope.launch {
                    viewModel.deleteUser(user)
                }
            }
            true
        }
    )

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart, DismissDirection.StartToEnd),
        background = {
            val color = when (dismissState.dismissDirection) {
                DismissDirection.StartToEnd -> MaterialTheme.colors.primary
                DismissDirection.EndToStart -> MaterialTheme.colors.error
                null -> MaterialTheme.colors.surface
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(16.dp)
            ) {
                if (dismissState.dismissDirection == DismissDirection.EndToStart) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Icon",
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Icon",
                        modifier = Modifier.align(Alignment.CenterStart)
                    )
                }
            }
        },
        dismissContent = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        navController.navigate(WeatherScreens.Weather.route)
                    }, elevation = 5.dp
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "${user.firstName} ${user.lastName}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = user.email)
                    }
                }
            }
        }
    )
}
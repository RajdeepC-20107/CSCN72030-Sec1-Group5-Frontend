package ru.anton2319.absolutcinema.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ru.anton2319.absolutcinema.model.SampleData
import ru.anton2319.absolutcinema.ui.calibrate.CalibrateScreen
import ru.anton2319.absolutcinema.ui.components.PlaceholderScreen
import ru.anton2319.absolutcinema.ui.devices.DeviceListScreen
import ru.anton2319.absolutcinema.ui.devices.DeviceReadingsScreen
import ru.anton2319.absolutcinema.ui.home.HomeScreen
import ru.anton2319.absolutcinema.ui.interrupt.EventLogScreen
import ru.anton2319.absolutcinema.ui.interrupt.InterruptModeScreen
import ru.anton2319.absolutcinema.ui.profile.ProfileScreen

object Routes {
    const val HOME = "home"
    const val CALIBRATE = "calibrate"
    const val PROFILE = "profile"
    const val ACCOUNT = "profile/account"
    const val INTERRUPT_MODE = "interrupt-mode"
    const val EVENT_LOG = "event-log"
    const val DEVICES = "devices"
    const val ADD_DEVICE = "devices/add"
    const val CREATE_GROUP = "devices/group"

    const val DEVICE_ID = "deviceId"
    const val DEVICE = "device/{$DEVICE_ID}"
    fun device(id: String) = "device/$id"
}

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Routes.HOME) {

        composable(Routes.HOME) {
            HomeScreen(
                state = SampleData.home,
                onProfileClick = { navController.navigate(Routes.PROFILE) { launchSingleTop = true } },
                onCalibrateClick = { navController.navigate(Routes.CALIBRATE) { launchSingleTop = true } },
                onSeeAllDevicesClick = { navController.navigate(Routes.DEVICES) { launchSingleTop = true } },
            )
        }

        composable(Routes.CALIBRATE) {
            CalibrateScreen(
                state = SampleData.calibration,
                onCancel = { navController.popBackStack() },
            )
        }

        composable(Routes.PROFILE) {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onAccountClick = { navController.navigate(Routes.ACCOUNT) },
                onInterruptModeClick = { navController.navigate(Routes.INTERRUPT_MODE) },
                onEventLogClick = { navController.navigate(Routes.EVENT_LOG) },
                onLogOut = { /* TODO: sign out, then navigate to a login screen */ },
            )
        }
        composable(Routes.ACCOUNT) {
            PlaceholderScreen("Profile", "Account details will go here.", onBack = { navController.popBackStack() })
        }
        composable(Routes.INTERRUPT_MODE) {
            InterruptModeScreen(rules = SampleData.interruptRules, onBack = { navController.popBackStack() })
        }
        composable(Routes.EVENT_LOG) {
            EventLogScreen(entries = SampleData.eventLog, onBack = { navController.popBackStack() })
        }

        composable(Routes.DEVICES) {
            DeviceListScreen(
                state = SampleData.deviceList,
                onBack = { navController.popBackStack() },
                onDeviceClick = { id -> navController.navigate(Routes.device(id)) },
                onAddDevice = { navController.navigate(Routes.ADD_DEVICE) },
                onCreateGroup = { navController.navigate(Routes.CREATE_GROUP) },
            )
        }
        composable(Routes.ADD_DEVICE) {
            PlaceholderScreen("Add device", "Pairing and discovery will go here.", onBack = { navController.popBackStack() })
        }
        composable(Routes.CREATE_GROUP) {
            PlaceholderScreen("Create group", "Pick devices to control together.", onBack = { navController.popBackStack() })
        }

        composable(
            route = Routes.DEVICE,
            arguments = listOf(navArgument(Routes.DEVICE_ID) { type = NavType.StringType }),
        ) { entry ->
            val id = entry.arguments?.getString(Routes.DEVICE_ID).orEmpty()
            DeviceReadingsScreen(
                state = SampleData.deviceDetail(id),
                onBack = { navController.popBackStack() },
                onRemove = { /* TODO: remove device, then */ navController.popBackStack() },
            )
        }
    }
}

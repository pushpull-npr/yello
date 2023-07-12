package presentation.component.dropdown

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Preview
@Composable
fun YelloDropdownItem(
	text : String,
	icon : String,
	onClick : () -> Unit,
) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier
			.fillMaxWidth()
			.clickable { onClick() }
			.padding(horizontal = 12.dp, vertical = 12.dp)
	) {
		Icon(
			painter = painterResource(icon),
			contentDescription = text,
			modifier = Modifier.requiredSize(20.dp)
		)
		Spacer(modifier = Modifier.width(8.dp))
		Text(
			text = text,
			style = MaterialTheme.typography.bodyMedium,
		)
	}
}

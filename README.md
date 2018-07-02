# PickSnacks
This app allows user to selected snacks from two list, veggis and non-veggis.

At the top, there are two option buttons to show/hide different categories of snacks, veggie or non-veggie.

At the bottom of the screen, there is "Submit" button to show what user selected.

There is "Add" menu button at the top right of the menu bar. "Add" button allows user to add new snack, veggie or non-veggie.

This implementation uses RecycleView, adapter, ArrayList and custom dialog.

Components:
1. Snack
2. Snack List
3. Snack Adapter
4. RecycleView

These scenarios are checked:
1. User clicks “Submit” button without selecting any snack
2. User tries to add a new snack with an already exist snack name

Todo:
1. Support serialization to store the newly added snack.
2. When turn on veggie or non-veggie to publish the snack list, need to check if user already add the snack.
3. Clean up codes to move logic to appropriate module

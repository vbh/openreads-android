package software.mdev.bookstracker.ui.bookslist.dialogs

import software.mdev.bookstracker.data.db.entities.Book

interface AddFoundBookDialogListener {
    fun onSaveButtonClicked(item: Book)
}
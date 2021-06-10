package software.mdev.bookstracker.ui.bookslist.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import software.mdev.bookstracker.R
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.ContextCompat
import software.mdev.bookstracker.data.db.entities.Book
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.dialog_add_book.*
import software.mdev.bookstracker.other.Constants
import software.mdev.bookstracker.other.Constants.BOOK_STATUS_IN_PROGRESS
import software.mdev.bookstracker.other.Constants.BOOK_STATUS_NOTHING
import software.mdev.bookstracker.other.Constants.BOOK_STATUS_READ
import software.mdev.bookstracker.other.Constants.BOOK_STATUS_TO_READ
import software.mdev.bookstracker.other.Constants.DATABASE_EMPTY_VALUE


class AddBookDialog(context: Context, var addBookDialogListener: AddBookDialogListener) : AppCompatDialog(context) {

    var whatIsClicked: String = BOOK_STATUS_NOTHING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_add_book)
        var accentColor = getAccentColor(context.applicationContext)

        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        rbAdderRating.visibility = View.GONE
        tvRateThisBook.visibility = View.GONE

        ivBookStatusSetRead.setOnClickListener {
            ivBookStatusSetRead.setColorFilter(accentColor, android.graphics.PorterDuff.Mode.SRC_IN)
            ivBookStatusSetInProgress.setColorFilter(ContextCompat.getColor(context, R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
            ivBookStatusSetToRead.setColorFilter(ContextCompat.getColor(context, R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
            whatIsClicked = BOOK_STATUS_READ
            rbAdderRating.visibility = View.VISIBLE
            tvRateThisBook.visibility = View.VISIBLE
            it.hideKeyboard()
        }

        ivBookStatusSetInProgress.setOnClickListener {
            ivBookStatusSetRead.setColorFilter(ContextCompat.getColor(context, R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
            ivBookStatusSetInProgress.setColorFilter(accentColor, android.graphics.PorterDuff.Mode.SRC_IN)
            ivBookStatusSetToRead.setColorFilter(ContextCompat.getColor(context, R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
            whatIsClicked = BOOK_STATUS_IN_PROGRESS
            rbAdderRating.visibility = View.GONE
            tvRateThisBook.visibility = View.GONE
            it.hideKeyboard()
        }

        ivBookStatusSetToRead.setOnClickListener {
            ivBookStatusSetRead.setColorFilter(ContextCompat.getColor(context, R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
            ivBookStatusSetInProgress.setColorFilter(ContextCompat.getColor(context, R.color.grey), android.graphics.PorterDuff.Mode.SRC_IN)
            ivBookStatusSetToRead.setColorFilter(accentColor, android.graphics.PorterDuff.Mode.SRC_IN)
            whatIsClicked = BOOK_STATUS_TO_READ
            rbAdderRating.visibility = View.GONE
            tvRateThisBook.visibility = View.GONE
            it.hideKeyboard()
        }

        btnAdderSaveBook.setOnClickListener {
            val bookTitle = etAdderBookTitle.text.toString()
            val bookAuthor = etAdderAuthor.text.toString()
            var bookRating = 0.0F

            if (bookTitle.isNotEmpty()) {
                if (bookAuthor.isNotEmpty()){
                    if (whatIsClicked != BOOK_STATUS_NOTHING) {
                        when(whatIsClicked){
                            BOOK_STATUS_READ -> bookRating = rbAdderRating.rating
                            BOOK_STATUS_IN_PROGRESS -> bookRating = 0.0F
                            BOOK_STATUS_TO_READ -> bookRating = 0.0F
                        }
                        val editedBook = Book(bookTitle, bookAuthor, bookRating, bookStatus = whatIsClicked, bookPriority = DATABASE_EMPTY_VALUE, bookStartDate = DATABASE_EMPTY_VALUE, bookFinishDate = DATABASE_EMPTY_VALUE)
                        addBookDialogListener.onSaveButtonClicked(editedBook)
                        dismiss()
                    } else {
                        Snackbar.make(it, R.string.sbWarningState, Snackbar.LENGTH_SHORT).show()
                    }
                } else {
                    Snackbar.make(it, R.string.sbWarningAuthor, Snackbar.LENGTH_SHORT).show()
                }
            } else {
                Snackbar.make(it, R.string.sbWarningTitle, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }

    fun getAccentColor(context: Context): Int {

        var accentColor = ContextCompat.getColor(context, R.color.purple_500)

        val sharedPref = context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

        var accent = sharedPref?.getString(
            Constants.SHARED_PREFERENCES_KEY_ACCENT,
            Constants.THEME_ACCENT_DEFAULT
        ).toString()

        when(accent){
            Constants.THEME_ACCENT_AMBER_500 -> accentColor = ContextCompat.getColor(context, R.color.amber_500)
            Constants.THEME_ACCENT_BLUE_500 -> accentColor = ContextCompat.getColor(context, R.color.blue_500)
            Constants.THEME_ACCENT_CYAN_500 -> accentColor = ContextCompat.getColor(context, R.color.cyan_500)
            Constants.THEME_ACCENT_GREEN_500 -> accentColor = ContextCompat.getColor(context, R.color.green_500)
            Constants.THEME_ACCENT_INDIGO_500 -> accentColor = ContextCompat.getColor(context, R.color.indigo_500)
            Constants.THEME_ACCENT_LIME_500 -> accentColor = ContextCompat.getColor(context, R.color.lime_500)
            Constants.THEME_ACCENT_PINK_500 -> accentColor = ContextCompat.getColor(context, R.color.pink_500)
            Constants.THEME_ACCENT_PURPLE_500 -> accentColor = ContextCompat.getColor(context, R.color.purple_500)
            Constants.THEME_ACCENT_TEAL_500 -> accentColor = ContextCompat.getColor(context, R.color.teal_500)
            Constants.THEME_ACCENT_YELLOW_500 -> accentColor = ContextCompat.getColor(context, R.color.yellow_500)
        }
        return accentColor
    }
}
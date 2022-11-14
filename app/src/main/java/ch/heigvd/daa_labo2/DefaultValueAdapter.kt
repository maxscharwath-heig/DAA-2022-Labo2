package ch.heigvd.daa_labo2

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

/**
 * Adapter for default value in spinner
 *
 * @author Nicolas Crausaz
 * @author Lazar Pavicevic
 * @author Maxime Scharwath
 */
class DefaultValueAdapter<T>(
    context: Context,
    resource: Int,
    defaultValue: T,
    objects: Array<T>
) : ArrayAdapter<T>(context, resource) {
    init {
        add(defaultValue)
        addAll(*objects)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        if(position == 0) {
            val v = View(context)
            v.visibility = View.GONE
            return v
        }
        return super.getDropDownView(position, null, parent)
    }

    override fun isEnabled(position: Int): Boolean {
        if (position == 0) {
            return false
        }
        return super.isEnabled(position)
    }
}
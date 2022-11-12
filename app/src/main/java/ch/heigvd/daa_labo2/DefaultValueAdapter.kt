package ch.heigvd.daa_labo2

import android.content.Context
import android.widget.ArrayAdapter

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

    override fun isEnabled(position: Int): Boolean {
        if (position == 0) {
            return false
        }
        return super.isEnabled(position)
    }
}
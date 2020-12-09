package com.example.organizeit

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.item_categoria.view.*

class CategoriasAdapter(private val mContext: Context, private val listaCategorias: List<Categorias>): ArrayAdapter<Categorias>(mContext,0, listaCategorias) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.item_categoria, parent, false)

        val categoria=listaCategorias[position]

        layout.idEdCategoria.text = categoria.nombre
        layout.idEdItem.text = categoria.items
        layout.imageView.setImageResource(categoria.imagen)

        return layout
    }
}
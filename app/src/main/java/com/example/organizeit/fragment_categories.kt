package com.example.organizeit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_categories.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BlankFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment_categories : Fragment() , View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

            val categoria= Categorias(nombre = "Familia", items = "0 / 6", imagen =  R.drawable.edit)
            val categoria2= Categorias(nombre = "Trabajo", items = "4 / 5", imagen =  R.drawable.edit)
            val categoria3= Categorias(nombre = "Amigos", items = "2 / 2", imagen =  R.drawable.edit)

            val listaCategorias = listOf(categoria, categoria2, categoria3)

            val adapter = CategoriasAdapter(mContext = requireActivity(), listaCategorias = listaCategorias)
            lvCategories.adapter = adapter

        }



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val myView = inflater.inflate(R.layout.fragment_categories, container, false)
        val addCatBTN = myView?.findViewById<View>(R.id.buttonAgregar)
        if (addCatBTN != null) {
            Toast.makeText(requireContext(), "Button found", Toast.LENGTH_SHORT).show()
            addCatBTN.setOnClickListener(this)
        }else{
            Toast.makeText(requireContext(), "Button not found", Toast.LENGTH_SHORT).show()
        }
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val categoria= Categorias(nombre = "Familia", items = "0 / 6", imagen =  R.drawable.edit)
        val categoria2= Categorias(nombre = "Trabajo", items = "4 / 5", imagen =  R.drawable.edit)
        val categoria3= Categorias(nombre = "Amigos", items = "2 / 2", imagen =  R.drawable.edit)

        val dbMan = DBManager(requireContext())
        val list = ArrayList<Categorias>()

        val categoriesStr = dbMan.getUserCat(dbMan.GetUserId().toString())
        for (cat in categoriesStr) {
            list.add(Categorias(nombre = cat, items = "0 / 6", imagen =  R.drawable.edit))
        }




        val listaCategorias = listOf(categoria, categoria2, categoria3)
        val listViewTest = this.lvCategories as ListView

        val adapter = CategoriasAdapter(requireContext(),list)

        listViewTest.adapter = adapter
    }

    companion object {

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            fragment_categories().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

    }

    override fun onClick(p0: View?) {
        val menuActivity = Intent(requireContext(), Activity_CreateCategory::class.java)
        startActivity(menuActivity)
    }


}

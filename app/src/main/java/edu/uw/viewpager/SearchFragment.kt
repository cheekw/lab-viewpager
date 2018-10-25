package edu.uw.viewpager


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

class SearchFragment : Fragment() {
    private var callback: SearchFragment.OnSearchListener? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_search, container, false)
        val button = rootView.findViewById<Button>(R.id.btn_search)
        button.setOnClickListener {
            val searchBar = rootView.findViewById<View>(R.id.txt_search) as EditText
            val text = searchBar.text.toString()
            callback!!.onSearchSubmitted(text)
        }
        return rootView
    }

    internal interface OnSearchListener {
        fun onSearchSubmitted(searchTerm: String)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            callback = context as SearchFragment.OnSearchListener?
        } catch (e: ClassCastException) {
            throw ClassCastException(context!!.toString() + " must implement OnSearchListener")
        }
    }

    companion object {
        fun newInstance(): SearchFragment {
            return SearchFragment()
        }
    }
}

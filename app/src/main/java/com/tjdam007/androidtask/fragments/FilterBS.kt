package com.tjdam007.androidtask.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tjdam007.androidtask.Callback
import com.tjdam007.androidtask.R
import com.tjdam007.androidtask.adapter.ChipFilterAdapter
import com.tjdam007.androidtask.utils.DATA
import com.tjdam007.androidtask.utils.HEADER
import kotlinx.android.synthetic.main.bottomsheet_filter.*

class FilterBS : BottomSheetDialogFragment() {

    private val dataList = arrayListOf<HashMap<String, ArrayList<String>>>()
    var callback: Callback<HashSet<String>>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataList.add(HashMap<String, ArrayList<String>>().apply {
            put(HEADER, arrayListOf("Location"))
            put(DATA, arrayListOf("California", "New York", "Los Angeles", "Florida", "Michigan", "Indianapolis"))
        })

        dataList.add(HashMap<String, ArrayList<String>>().apply {
            put(HEADER, arrayListOf("Job Roles"))
            put(DATA, arrayListOf("Sales", "Back Office", "Telecaller", "Cook", "Chef", "Field Boy", "Delivery Boy"))
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottomsheet_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvChipFilter.layoutManager = LinearLayoutManager(context)
        val adapter = ChipFilterAdapter(context)
        rvChipFilter.adapter = adapter
        adapter.setData(dataList)
        imageClose.setOnClickListener {
            callback?.onCallback(HashSet<String>())
            dismiss()
        }

        chipContinue.setOnClickListener {
            Toast.makeText(context, R.string.please_wait_fetching_jobs, Toast.LENGTH_SHORT)
                .show()
            callback?.onCallback(adapter.selectedChips!!)
            dismiss()
        }

        chipCancel.setOnClickListener {
            callback?.onCallback(HashSet<String>())
            dismiss()
        }

        arguments?.apply {
            if (containsKey(DATA)) {
                val data = arguments?.getSerializable("Data") as HashSet<String>
                adapter.selectedChips.clear()
                adapter.selectedChips.addAll(data)
                adapter.notifyDataSetChanged()
            }
        }

        dialog?.setOnDismissListener({

        })
    }
}
package com.tjdam007.androidtask.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tjdam007.androidtask.Callback
import com.tjdam007.androidtask.R
import com.tjdam007.androidtask.adapter.FilterAdapter
import com.tjdam007.androidtask.adapter.JobAdapter
import com.tjdam007.androidtask.fragments.FilterBS
import com.tjdam007.androidtask.fragments.JobDetailsFragment
import com.tjdam007.androidtask.model.Job
import com.tjdam007.androidtask.utils.DATA
import com.tjdam007.androidtask.utils.getChipTextColor
import com.tjdam007.androidtask.utils.getFilterChipColor
import com.tjdam007.androidtask.utils.showToast
import com.tjdam007.androidtask.viewModels.LandingVM
import kotlinx.android.synthetic.main.activity_landing_page.*

class LandingActivity : AppCompatActivity() {

    lateinit var landingVM: LandingVM
    var page = 1
    var selectedFilter: HashSet<String>? = null

    private lateinit var observer: Observer<ArrayList<Job>>

    private lateinit var filterAdapter: FilterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page)

        setSupportActionBar(toolBar)
        imageSearch.visibility = View.INVISIBLE
        buttonFilter.apply {
            setTextColor(getChipTextColor(context))
            chipStrokeColor = getFilterChipColor(context)
        }
        buttonFilter.setOnClickListener {
            val filterBS = FilterBS()
            if (!selectedFilter.isNullOrEmpty()) {
                val bundle = Bundle()
                bundle.putSerializable(DATA, selectedFilter!!)
                filterBS.arguments = bundle
            }
            filterBS.callback = object : Callback<HashSet<String>> {
                override fun onCallback(data: HashSet<String>) {
                    this@LandingActivity.selectedFilter = data
                    var location = ""
                    if (!data.isNullOrEmpty()) {
                        location = data.first()
                        val adapter = FilterAdapter(this@LandingActivity)
                        adapter.list = ArrayList(data)
                        adapter.isChecked = true
                        rvFilter.swapAdapter(adapter, true)
                    } else {
                        filterAdapter.isChecked = false
                        rvFilter.swapAdapter(filterAdapter, true)
                        buttonFilter.isChecked = false
                    }
                    landingVM.getJobs(location, page = page)
                        .observe(this@LandingActivity, this@LandingActivity.observer)
                }
            }
            filterBS.isCancelable = false
            filterBS.show(supportFragmentManager, "FilterBS")
        }


        landingVM = ViewModelProviders.of(this).get(LandingVM::class.java)

        rvFilter.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        filterAdapter = FilterAdapter(this)
        filterAdapter.callback = object : Callback<String> {
            override fun onCallback(data: String) {
            }
        }
        rvFilter.adapter = filterAdapter

        //filters
        landingVM.getFilters().observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                filterAdapter.list = it
                filterAdapter.notifyDataSetChanged()
            }
        })

        rvJobs.layoutManager = LinearLayoutManager(this)
        val jobAdapter = JobAdapter(this)
        rvJobs.adapter = jobAdapter
        jobAdapter.callback = object : Callback<Job> {
            override fun onCallback(data: Job) {
                val sheet = JobDetailsFragment()
                sheet.show(supportFragmentManager, JobDetailsFragment.TAG)
            }
        }

        //get all jobs
        tvNoJobs.text = getString(R.string.please_wait_fetching_jobs)
        tvNoJobs.animate()


        //data observer for any change made in db
        observer = Observer<ArrayList<Job>>() {
            if (it.isNullOrEmpty()) {
                tvNoJobs.visibility = View.VISIBLE
                rvJobs.visibility = View.INVISIBLE
            } else {
                tvNoJobs.visibility = View.GONE
                rvJobs.visibility = View.VISIBLE
                if (!selectedFilter.isNullOrEmpty()) {
                    jobAdapter.list.clear()
                }
                jobAdapter.list.addAll(it)
                jobAdapter.notifyDataSetChanged()
                supportActionBar?.setTitle(getString(R.string.jobs_found,jobAdapter.list.size))
            }
        }

        //calling first time
        landingVM.getJobs(page = page).observe(this, observer)

        rvJobs.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(rv, dx, dy)
                (rv.layoutManager as LinearLayoutManager).apply {
                    if (this.findLastCompletelyVisibleItemPosition() == jobAdapter.list.size.minus(1)) {
                        landingVM.getJobs(page = page++).observe(this@LandingActivity, observer)
                        showToast(R.string.getting_jobs)
                    }
                }
            }
        })

        rvFilter.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(rv: RecyclerView, newState: Int) {
                super.onScrollStateChanged(rv, newState)
                (rv.layoutManager as LinearLayoutManager).apply {
                    if (findFirstVisibleItemPosition() != 0) {
                        buttonFilter.text = ""
                    } else {
                        buttonFilter.text = getString(R.string.filter)
                    }
                }
            }
        })


        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length!! == 0) {
                    jobAdapter.list.clear()
                    jobAdapter.notifyDataSetChanged()
                    landingVM.getJobs(page = page).observe(this@LandingActivity, observer)
                    tvNoJobs.visibility = View.VISIBLE
                    tvNoJobs.text = getString(R.string.getting_jobs)
                    imageSearch.visibility = View.INVISIBLE
                } else {
                    imageSearch.visibility = View.VISIBLE
                }
            }
        })

        imageSearch.setOnClickListener {
            if (etSearch.text.toString().length >= 3) {
                jobAdapter.list.clear()
                jobAdapter.notifyDataSetChanged()
                landingVM.searchJob(etSearch.text.toString()).observe(this@LandingActivity, observer)
                tvNoJobs.visibility = View.VISIBLE
                tvNoJobs.text = getString(R.string.searching)
            } else {
                showToast(R.string.please_enter)
            }
        }

    }
}
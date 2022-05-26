package com.masharo.habits.presentation.listHabit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.masharo.habits.R
import com.masharo.habits.app.App
import com.masharo.habits.databinding.FragmentSortAndSearchBinding
import com.masharo.habits.presentation.HabitListFilter
import javax.inject.Inject

class SortAndSearchFragment : Fragment() {

    private lateinit var bind: FragmentSortAndSearchBinding
    //    private val vm: HabitListViewModel by sharedViewModel()
    @Inject
    lateinit var vmFactory: HabitListViewModelFactory
    private val vm: HabitListViewModel by lazy {
        ViewModelProvider(activity?.viewModelStore ?: throw NullPointerException(
            "Activity is null. SortAndSearchFragment"
        ), vmFactory)
        .get(HabitListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.apply {
            (applicationContext as App).appComponent.inject(this@SortAndSearchFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bind = FragmentSortAndSearchBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.let {
            bind.spinnerSortAndSearchSort.adapter = ArrayAdapter(
                it,
                R.layout.habit_spinner_item,
                HabitListFilter.useOperationToString(HabitListFilter.Operation.ORDER_BY, it)
            )
        }

        bind.spinnerSortAndSearchSort.onItemSelectedListener = object: OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                vm.setSort(HabitListFilter.columnsOrderBy[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        bind.editTextSortAndSearchSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {}
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    s?.let {
                        vm.setSearch(s.toString()) }
                }
            }
        )
    }
}
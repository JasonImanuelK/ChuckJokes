package com.hans.chuckjokes.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hans.chuckjokes.core.data.source.remote.network.ApiResponse
import com.hans.chuckjokes.core.ui.JokesAdapter
import com.hans.chuckjokes.core.ui.ViewModelFactory
import com.hans.chuckjokes.databinding.FragmentResultBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultFragment : Fragment() {
    private lateinit var resultViewModel: ResultViewModel
    private val jokesAdapter = JokesAdapter()

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val QUERY = "query"

        fun newInstance(query: String): ResultFragment {
            val fragment = ResultFragment()
            val bundle = Bundle()
            bundle.putString(QUERY, query)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = arguments?.getString(QUERY)
        setupRecyclerView()

        val factory = ViewModelFactory.getInstance(requireActivity(), query.toString())
        resultViewModel = ViewModelProvider(this, factory)[ResultViewModel::class.java]

        resultViewModel.jokes.observe(viewLifecycleOwner) { jokes ->
            if (jokes.data != null) {
                jokesAdapter.setData(jokes.data)
                Log.v("Hasil JOKES DATA :", jokes.data.toString())
            } else {
                Toast.makeText(requireContext(), "Jokes data is null", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvResult) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = jokesAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
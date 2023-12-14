package com.hans.chuckjokes.favorite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hans.chuckjokes.R
import com.hans.chuckjokes.core.ui.JokesAdapter
import com.hans.chuckjokes.core.ui.ViewModelFactory
import com.hans.chuckjokes.databinding.FragmentFavoriteBinding
import com.hans.chuckjokes.home.MainActivity
import com.hans.chuckjokes.search.ResultFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val jokesAdapter = JokesAdapter()

            val factory = ViewModelFactory.getInstance(requireActivity())
            favoriteViewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

            favoriteViewModel.favoriteJokes.observe(viewLifecycleOwner, { jokes ->
                jokesAdapter.setData(jokes)
                Log.v("MASUK ", jokes.toString())

                // Log visibility state
                Log.v("ViewEmpty Visibility", binding.viewEmpty.root.visibility.toString())
                Log.v("RecyclerView Visibility", binding.rvJokes.visibility.toString())

                // Update the visibility state based on data
                binding.viewEmpty.root.visibility = if (jokes.isNotEmpty()) View.GONE else View.VISIBLE

                // Notify the adapter about the data change
                jokesAdapter.notifyDataSetChanged()
            })

            with(binding.rvJokes) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = jokesAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
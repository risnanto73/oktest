package com.tiorisnanto.myapplication.ui.home.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.tiorisnanto.myapplication.R
import com.tiorisnanto.myapplication.databinding.FragmentNoteBinding
import com.tiorisnanto.myapplication.ui.home.fragment.note.CustomAdapter
import com.tiorisnanto.myapplication.ui.home.fragment.note.DBHelper
import com.tiorisnanto.myapplication.ui.home.fragment.note.DetailsActivity

class NoteFragment : Fragment() {

    private lateinit var binding: FragmentNoteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

}
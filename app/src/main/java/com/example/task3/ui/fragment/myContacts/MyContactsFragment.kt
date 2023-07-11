package com.example.task3.ui.fragment.myContacts

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task3.data.contacts.model.Contact
import com.example.task3.R
import com.example.task3.databinding.FragmentMyContactsBinding
import com.example.task3.ui.fragment.addContact.AddContactDialogFragment
import com.example.task3.ui.fragment.addContact.ConfirmationListener
import com.example.task3.ui.fragment.myContacts.adapter.ContactActionListener
import com.example.task3.ui.fragment.myContacts.adapter.ContactAdapter
import com.example.task3.ui.utils.ext.logExt
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MyContactsFragment : Fragment(R.layout.fragment_my_contacts), ConfirmationListener {

    private lateinit var binding : FragmentMyContactsBinding
    private lateinit var adapter: ContactAdapter

    private val contactViewModel : MyContactsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyContactsBinding.bind(view)

        bindRecycleView()
        observeViewModel()
        addSwipeToDelete()
        setListeners()
    }

    private fun setListeners() {
        binding.fragmentMyContactTextViewAddContact.setOnClickListener { startDialogAddContact() }
        binding.fragmentMyContactsImageViewBack.setOnClickListener { imageViewBackListener() }
        binding.fragmentMyContactImageViewSearch.setOnClickListener {  } // TODO: search in contacts 
    }

    private fun imageViewBackListener() {
        findNavController().popBackStack()
    }

    private fun startDialogAddContact() {
        val addContactDialogFragment = AddContactDialogFragment()
        addContactDialogFragment.show(childFragmentManager, AddContactDialogFragment.TAG)
    }

    private fun bindRecycleView() {
        adapter = ContactAdapter(contactActionListener = object : ContactActionListener {
            override fun onContactDelete(contact: Contact) {
                val index = contactViewModel.getContactIndex(contact)
                contactViewModel.deleteContact(contact)
                showDeleteMessage(index, contact)
            }

            override fun onContactClick(contact: Contact) {
                logExt(contact.toString())
                logExt(contactViewModel.getContactIndex(contact).toString())
                val direction = MyContactsFragmentDirections
                    .actionMyContactsFragmentToContactProfileFragment(contact)

                findNavController().navigate(direction)
            }
        })

        val recyclerLayoutManager = LinearLayoutManager(activity)
        with(binding){
            fragmentMyContactRecyclerViewContacts.layoutManager = recyclerLayoutManager
            fragmentMyContactRecyclerViewContacts.adapter = adapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                contactViewModel.contacts.collect {
                    adapter.submitList(it)
                }
            }
        }
    }

    private fun addSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.END or ItemTouchHelper.START) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            )
                    : Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val index = viewHolder.adapterPosition
                val contact: Contact = contactViewModel.getContact(index)

                contactViewModel.deleteContact(contact)
                showDeleteMessage(index, contact)
            }
        }).attachToRecyclerView(binding.fragmentMyContactRecyclerViewContacts)
    }

    private fun showDeleteMessage(index: Int, contact: Contact) {
        Snackbar.make(binding.root, R.string.message_delete, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.snackbar_action).uppercase()) {
                contactViewModel.addContact(index, contact)
            }.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.contacts_activity_class_snackbar_action_color))
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.contacts_activity_class_snackbar_text_color))
            .show()
    }

    override fun onConfirmButtonClicked(contact: Contact) {
        contactViewModel.addContact(contact)
        Snackbar.make(binding.root, R.string.message_add_contact, Snackbar.LENGTH_SHORT)
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.contacts_activity_class_snackbar_text_color))
            .show()
    }
}
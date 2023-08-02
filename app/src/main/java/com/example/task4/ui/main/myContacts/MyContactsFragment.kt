package com.example.task4.ui.main.myContacts

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task4.R
import com.example.task4.base.BaseFragment
import com.example.task4.constants.Constants.TAG
import com.example.task4.data.models.Contact
import com.example.task4.databinding.FragmentMyContactsBinding
import com.example.task4.ui.main.myContacts.adapter.ContactActionListener
import com.example.task4.ui.main.myContacts.adapter.ContactAdapter
import com.example.task4.ui.main.myContacts.addContact.AddContactDialogFragment
import com.example.task4.ui.main.myContacts.addContact.ConfirmationListener
import com.example.task4.ui.main.viewpager.ViewPagerFragment
import com.example.task4.ui.main.viewpager.ViewPagerFragmentDirections
import com.example.task4.ui.utils.ext.logExt
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MyContactsFragment :
    BaseFragment<FragmentMyContactsBinding>(FragmentMyContactsBinding::inflate),
    ConfirmationListener {

    private val contactViewModel : MyContactsViewModel by viewModels()
    private lateinit var contactAdapter: ContactAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        (parentFragment as ViewPagerFragment).openTab(0)
    }

    private fun startDialogAddContact() {
        val addContactDialogFragment = AddContactDialogFragment()
        addContactDialogFragment.show(childFragmentManager, TAG)
    }

    private fun bindRecycleView() {
        contactAdapter = ContactAdapter(contactActionListener = object : ContactActionListener {
            override fun onContactDelete(contact: Contact) {
                val index = contactViewModel.getContactIndex(contact)
                contactViewModel.deleteContact(contact)
                showDeleteMessage(index, contact)
            }

            override fun onContactClick(contact: Contact, transitionNames: Array<Pair<View, String>>) {
                with(contactViewModel) {
                    if (contactAdapter.isMultiSelectMode) {
                        toggle(contact)

                        if (sizeOfSelectedItems() == 0) {
                            changeMultiSelectMode()
                        }
                    } else {
                        val extras = FragmentNavigatorExtras(*transitionNames)

                        val direction: NavDirections = ViewPagerFragmentDirections
                            .actionViewPagerFragmentToContactProfileFragment(contact)

                        navController.navigate(direction, extras)
                    }
                }
            }

            override fun onContactLongClick(contact: Contact) {
                with(contactViewModel) {
                    changeMultiSelectMode()
                    clearSelectedItems()

                    logExt(isMultiSelectMode.value.toString())
                    logExt(contactAdapter.isMultiSelectMode.toString())
                    if (isMultiSelectMode.value) {
                        toggle(contact)
                    } else {
                        clearSelectedItems()
                    }
                }
            }
        })

        val recyclerLayoutManager = LinearLayoutManager(activity)
        with(binding.fragmentMyContactRecyclerViewContacts){
            layoutManager = recyclerLayoutManager
            adapter = contactAdapter
        }
    }
    private fun observeViewModel() {
        postponeEnterTransition()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    contactViewModel.contacts.collect {
                        contactAdapter.submitList(it)
                        startPostponedEnterTransition()
                    }
                }

                launch {
                    contactViewModel.isMultiSelectMode.collect {
                        binding.fragmentMyContactRecyclerViewContacts.adapter = contactAdapter
                        contactAdapter.isMultiSelectMode = it
                        binding.fragmentMyContactTextViewAddContact.visibility = if (it) GONE else VISIBLE
                    }
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
            ): Boolean = false

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
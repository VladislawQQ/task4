package com.example.task4.ui.main.viewpager.myContacts

import android.os.Bundle
import android.view.View
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
import com.example.task4.ui.main.viewpager.ViewPagerFragment
import com.example.task4.ui.main.viewpager.ViewPagerFragmentDirections
import com.example.task4.ui.main.viewpager.myContacts.adapter.ContactActionListener
import com.example.task4.ui.main.viewpager.myContacts.adapter.ContactAdapter
import com.example.task4.ui.main.viewpager.myContacts.addContact.AddContactDialogFragment
import com.example.task4.ui.main.viewpager.myContacts.addContact.ConfirmationListener
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

            override fun onContactDelete(contact: ContactListItem) {
                val index = contactViewModel.getContactIndex(contact.contact)
                contactViewModel.deleteContact(contact.contact)
                showDeleteMessage(index, contact.contact)
            }

            override fun onContactClick(contact: ContactListItem, transitionNames: Array<Pair<View, String>>) {
                    if (contactAdapter.isMultiSelectMode) {
                        contactViewModel.toggle(contact)
                        logExt("Click")
                    } else {
                        val extras = FragmentNavigatorExtras(*transitionNames)

                        val direction: NavDirections = ViewPagerFragmentDirections
                            .actionViewPagerFragmentToContactProfileFragment(contact.contact)

                        navController.navigate(direction, extras)
                    }
            }

            override fun onContactLongClick(contact: ContactListItem) {
                with(contactViewModel) {
                    if (contactAdapter.isMultiSelectMode) {
                        clearSelectedItems()
                    } else {
                        toggle(contact)
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
                contactViewModel.stateFlow.collect {  state ->
                    if (state != null) {
                        contactAdapter.submitList(state.contacts)
                        contactAdapter.isMultiSelectMode = state.totalCheckedCount > 0
                        startPostponedEnterTransition()
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
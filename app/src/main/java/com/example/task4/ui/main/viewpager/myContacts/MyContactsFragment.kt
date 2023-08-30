package com.example.task4.ui.main.viewpager.myContacts

import android.Manifest
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.task4.data.models.Contact
import com.example.task4.databinding.FragmentMyContactsBinding
import com.example.task4.ui.main.viewpager.ViewPagerFragment
import com.example.task4.ui.main.viewpager.ViewPagerFragmentDirections
import com.example.task4.ui.main.viewpager.myContacts.adapter.ContactActionListener
import com.example.task4.ui.main.viewpager.myContacts.adapter.ContactAdapter
import com.example.task4.ui.main.viewpager.myContacts.addContact.AddContactDialogFragment
import com.example.task4.ui.main.viewpager.myContacts.addContact.AddContactDialogFragment.Companion.TAG_ADD_CONTACT
import com.example.task4.ui.main.viewpager.myContacts.addContact.ConfirmationListener
import com.example.task4.ui.main.viewpager.myContacts.model.ContactListItem
import com.example.task4.ui.utils.Constants
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MyContactsFragment :
    BaseFragment<FragmentMyContactsBinding>(FragmentMyContactsBinding::inflate),
    ConfirmationListener {

    private val viewModel: MyContactsViewModel by viewModels()

    private val contactAdapter by lazy {
        ContactAdapter(contactActionListener = object :
            ContactActionListener {

            override fun onContactDelete(contact: ContactListItem) {
                viewModel.deleteContact(contact.contact)
                showDeleteMessage()
            }

            override fun onContactClick(
                contact: ContactListItem,
                transitionNames: Array<Pair<View, String>>
            ) {
                with(viewModel) {
                    if (isMultiselect.value == true) {
                        toggle(contact)
                        if (!contactsIsSelected())
                            swapIsMultiselect()
                    } else {
                        startContactProfileFragment(transitionNames, contact.contact)
                    }
                }
            }

            override fun onContactLongClick(contact: ContactListItem) {
                with(viewModel) {
                    if (isMultiselect.value == true) {
                        clearSelectedItems()
                    } else {
                        toggle(contact)
                    }
                    swapIsMultiselect()
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestReadContactsPermission() // ask permission for READ_CONTACTS
        bindRecycleView()               // create rv adapter and bind
        observeViewModel()
        addSwipeToDelete()
        setListeners()
    }

    /**
     * Start activity with request permission for read contacts from phonebook.
     */
    private fun requestReadContactsPermission() {
        permission.launch(Manifest.permission.READ_CONTACTS)
    }

    private fun setListeners() { // TODO: with
        with(binding) {
            fragmentMyContactTextViewAddContact.setOnClickListener { startDialogAddContact() }
            fragmentMyContactsImageViewBack.setOnClickListener { imageViewBackListener() }
            textViewGetContacts.setOnClickListener { requestReadContactsPermission() }
            imageViewBucket.setOnClickListener { viewModel.deleteSelectedItems() }
            fragmentMyContactImageViewSearch.setOnClickListener { } // TODO: search in contacts
            backPressedListener()
        }
    }

    private fun backPressedListener() {
        val callBack = object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (parentFragment as ViewPagerFragment).openTab(Constants.SCREENS.PROFILE_SCREEN.ordinal)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callBack)
    }

    private fun imageViewBackListener() {
        (parentFragment as ViewPagerFragment).openTab(Constants.SCREENS.PROFILE_SCREEN.ordinal) // TODO: to constants
    }

    private fun startDialogAddContact() {
        val addContactDialogFragment = AddContactDialogFragment()
        addContactDialogFragment.show(childFragmentManager, TAG_ADD_CONTACT)
    }

    private fun bindRecycleView() {
        val recyclerLayoutManager = LinearLayoutManager(requireContext())

        with(binding.fragmentMyContactRecyclerViewContacts) {
            layoutManager = recyclerLayoutManager
            adapter = contactAdapter
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collect { contacts ->
                    contactAdapter.submitList(contacts)
                }
            }
        }

        viewModel.isMultiselect.observe(viewLifecycleOwner) { isMultiselect ->
            contactAdapter.isMultiSelectMode = isMultiselect
            binding.imageViewBucket.visibility = if (isMultiselect) VISIBLE else GONE
            binding.fragmentMyContactRecyclerViewContacts.adapter = contactAdapter
        }
    }

    private fun addSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.END or ItemTouchHelper.START
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.deleteContact(viewHolder.adapterPosition)
                showDeleteMessage()
            }

            override fun isItemViewSwipeEnabled(): Boolean {
                return viewModel.isMultiselect.value == false // TODO: !! - bad
            }
        }).attachToRecyclerView(binding.fragmentMyContactRecyclerViewContacts)
    }

    private fun showDeleteMessage() {
        Snackbar.make(binding.root, R.string.message_delete, Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.snackbar_action).uppercase()) {
                viewModel.restoreContact()
            }.setActionTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.contacts_activity_class_snackbar_action_color
                )
            )
            .setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.contacts_activity_class_snackbar_text_color
                )
            )
            .show()
    }

    override fun onConfirmButtonClicked(contact: Contact) {
        viewModel.addContact(contact)
        Snackbar.make(binding.root, R.string.message_add_contact, Snackbar.LENGTH_SHORT)
            .setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.contacts_activity_class_snackbar_text_color
                )
            )
            .show()
    }

    private fun startContactProfileFragment(
        transitionNames: Array<Pair<View, String>>,
        contact: Contact
    ) {
        val extras = FragmentNavigatorExtras(*transitionNames)

        val direction: NavDirections = ViewPagerFragmentDirections
            .actionViewPagerFragmentToContactProfileFragment(contact)

        navController.navigate(direction, extras)
    }

    private val permission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) viewModel.setPhoneContacts()
        }


}
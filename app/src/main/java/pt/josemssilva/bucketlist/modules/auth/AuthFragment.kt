package pt.josemssilva.bucketlist.modules.auth

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.auth_fragment.*
import org.rekotlin.StoreSubscriber
import pt.josemssilva.bucketlist.R
import pt.josemssilva.bucketlist.common.AppState
import pt.josemssilva.bucketlist.store

class AuthFragment : Fragment(), StoreSubscriber<AppState> {
    companion object {
        fun newInstance(): AuthFragment {
            return AuthFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.auth_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        submit_button.setOnClickListener {
            store().dispatch(
                AuthAction.Login(
                    username = username.text.toString(),
                    password = password.text.toString()
                )
            )
        }
    }

    override fun onStart() {
        super.onStart()

        store().subscribe(this)
    }

    override fun onStop() {
        store().unsubscribe(this)

        super.onStop()
    }

    override fun newState(state: AppState) {
        state.auth?.error?.apply {
            AlertDialog.Builder(requireContext())
                .setMessage(this)
                .setPositiveButton(R.string.common_ok) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}
package br.com.auttar.sampleauttarsdk.payment.client.userInterface

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.navArgs
import br.com.auttar.sampleauttarsdk.base.BaseBindingFragment
import br.com.auttar.sampleauttarsdk.databinding.FragmentPaymentClientSelectOptionOperationBinding
import br.com.auttar.sampleauttarsdk.payment.PaymentViewModel
import br.com.auttar.sampleauttarsdk.payment.client.command.ClientAskSelectCommand
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class PaymentClientUserSelectOptionFragment :
    BaseBindingFragment<FragmentPaymentClientSelectOptionOperationBinding>() {

    private val args by navArgs<PaymentClientUserSelectOptionFragmentArgs>()
    private val paymentViewModel: PaymentViewModel by activityViewModel()

    override fun getBindingFragment(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPaymentClientSelectOptionOperationBinding {
        blockOnBackPressed()
        return FragmentPaymentClientSelectOptionOperationBinding.inflate(
            inflater,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val option = args.options

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), android.R.layout.select_dialog_item, option)

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(args.text)
        builder.setAdapter(
            adapter
        ) { _, index ->
            paymentViewModel.executeClientCommand(ClientAskSelectCommand(args.options[index]))
        }

        builder.create().show()
    }
}
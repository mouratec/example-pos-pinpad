package br.com.auttar.sampleauttarsdk.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import br.com.auttar.sampleauttarsdk.PaymentNavigationDirections
import br.com.auttar.sampleauttarsdk.R
import br.com.auttar.sampleauttarsdk.base.BaseBindingFragment
import br.com.auttar.sampleauttarsdk.databinding.FragmentPaymentBinding
import br.com.auttar.sampleauttarsdk.payment.client.PaymentClientUserInterfaceState
import br.com.auttar.sampleauttarsdk.payment.client.PaymentClientUserInterfaceViewModel
import br.com.auttar.sampleauttarsdk.payment.enums.ContactlessStatusEnum
import br.com.auttar.sampleauttarsdk.util.doNothing
import br.com.auttar.sampleauttarsdk.util.showToast
import br.com.auttar.sdk.interfaces.transaction.model.TefResultInterface
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class PaymentFragment : BaseBindingFragment<FragmentPaymentBinding>() {

    private val paymentViewModel: PaymentViewModel by activityViewModel()
    private val clientUserViewModel: PaymentClientUserInterfaceViewModel by activityViewModel()

    override fun getBindingFragment(
        inflater: LayoutInflater, container: ViewGroup?
    ): FragmentPaymentBinding {
        return FragmentPaymentBinding.inflate(inflater, container, false)
    }

    override fun getFragmentNavController(): NavController {
        val navHost =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment_content_payment) as NavHostFragment
        return navHost.navController
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paymentViewModel.paymentState.observe(viewLifecycleOwner) {
            verifyPaymentState(it)
        }

        clientUserViewModel.clientUserInterfaceState.observe(viewLifecycleOwner) {
            verifyClientUserState(it)
        }
    }

    private fun verifyPaymentState(paymentState: PaymentState?) {
        when (paymentState) {
            is PaymentState.OnError -> navigateToPaymentError(paymentState)
            is PaymentState.OnSuccess -> navigateToPaymentSuccess(paymentState)
            is PaymentState.OnMessage -> showToast(paymentState.message)
            else -> doNothing()
        }
    }

    private fun navigateToPaymentError(paymentState: PaymentState.OnError) {
        val error = paymentState.data.toTefResultData()
        val direction = PaymentErrorFragmentDirections.actionToPaymentErrorPayment(error)
        navToAction(direction)
    }

    private fun navigateToPaymentSuccess(paymentState: PaymentState.OnSuccess) {
        val params = paymentState.data.toTefResultData()
        val direction = PaymentSuccessFragmentDirections.actionToPaymentSuccessPayment(params)
        navToAction(direction)
    }

    private fun TefResultInterface.toTefResultData(): TefResultData {
        return TefResultData(
            transactionCode = transacionCode,
            amount = amount,
            nsuCTF = nsuCTF,
            dateSdk = dateSdk,
            display = display,
            messageAction = messageAction,
            authorizedNSU = authorizedNSU,
            codSupport = codSupport,
            transactionId = pixTransactionId,
            receiverPSP = pixReceiverPSP
        )
    }

    private fun verifyClientUserState(clientUserInterfaceState: PaymentClientUserInterfaceState?) {
        when (clientUserInterfaceState) {
            is PaymentClientUserInterfaceState.OnAskForConfirm -> {
                navToAction(PaymentNavigationDirections.actionToPaymentClientUserCancelOperation())
            }

            is PaymentClientUserInterfaceState.OnAskForDate -> {
                navToAction(
                    PaymentNavigationDirections.actionToPaymentClientUserInsertDate(
                        clientUserInterfaceState.text, clientUserInterfaceState.hint
                    )
                )
            }

            is PaymentClientUserInterfaceState.OnAskForValue -> {
                navToAction(
                    PaymentNavigationDirections.actionToPaymentClientUserCaptureAskValue(
                        clientUserInterfaceState.text
                    )
                )
            }

            is PaymentClientUserInterfaceState.OnAskGenericCapture -> {
                navToAction(
                    PaymentNavigationDirections.actionToPaymentClientUserCaptureString(
                        clientUserInterfaceState.text, clientUserInterfaceState.mask
                    )
                )
            }

            is PaymentClientUserInterfaceState.OnAskSelect -> {
                navToAction(
                    PaymentNavigationDirections.actionToPaymentClientSelectOption(
                        clientUserInterfaceState.text,
                        clientUserInterfaceState.options.toTypedArray()
                    )
                )
            }

            is PaymentClientUserInterfaceState.OnCleanDisplay -> {
                navToAction(
                    PaymentNavigationDirections.actionToPaymentClientUserLoading().setText("")
                )
            }

            is PaymentClientUserInterfaceState.OnHideQrCode -> {
                navToAction(
                    PaymentNavigationDirections.actionToPaymentClientUserLoading().setText("")
                )
            }

            is PaymentClientUserInterfaceState.OnMessageDisplay -> {
                navToAction(
                    PaymentNavigationDirections.actionToPaymentClientUserLoading()
                        .setText(clientUserInterfaceState.text)
                )
            }

            is PaymentClientUserInterfaceState.OnShowInsertCardMessage -> {
                navToAction(
                    PaymentNavigationDirections.actionToPaymentClientUserInsertCard(
                        clientUserInterfaceState.text
                    )
                )
            }

            is PaymentClientUserInterfaceState.OnShowPin -> {
                navToAction(
                    PaymentNavigationDirections.actionToPaymentClientUserInsertPass(
                        clientUserInterfaceState.text
                    )
                )
            }

            is PaymentClientUserInterfaceState.ShowContactless -> {
                val text = ContactlessStatusEnum.valueOf(clientUserInterfaceState.text).stringResId
                navToAction(
                    PaymentNavigationDirections.actionToPaymentClientUserInsertCard(
                        getString(text)
                    )
                )
            }

            else -> {}
        }
    }
}
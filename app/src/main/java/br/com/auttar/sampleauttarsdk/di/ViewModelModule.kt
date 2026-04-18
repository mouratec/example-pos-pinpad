package br.com.auttar.sampleauttarsdk.di

import br.com.auttar.sampleauttarsdk.authentication.AuthenticationViewModel
import br.com.auttar.sampleauttarsdk.payment.PaymentViewModel
import br.com.auttar.sampleauttarsdk.payment.client.PaymentClientUserInterfaceViewModel
import br.com.auttar.sampleauttarsdk.payment.refund.RefundViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

class ViewModelModule {

    fun provider() = module {
        viewModel { AuthenticationViewModel(auttarSdk = get(), auttarSdkConfig = get()) }
        viewModel { PaymentViewModel(auttarSdk = get()) }
        viewModel { PaymentClientUserInterfaceViewModel(auttarSdk = get()) }
        viewModel { RefundViewModel() }
    }
}
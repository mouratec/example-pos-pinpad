# Guia de Integração do SDK
#
[![N|Solid](https://auttar.com.br/wp-content/uploads/2024/01/logo-auttar.svg)](https://auttar.com.br/)
#### Utilizando POS Android / Pinpad
#
> Versão: ` 1.0.0`
#

------------
#

# 📖 Introdução


Este documento serve como **referência técnica** e **material de apoio** para desenvolvedores que desejam implementar o **SDK da Auttar** em suas aplicações.

O pacote disponibilizado contém:

- O SDK da Auttar
- Suas dependências
- A documentação técnica
- Uma aplicação de exemplo com integração funcional

O SDK foi projetado para fornecer uma interface simples e segura para a execução de transações de pagamento por aproximação (contactless), inserção de cartão (chip) e leitura de tarja magnética. Isso permite que desenvolvedores Android integrem essas funcionalidades sem se preocupar com o tratamento direto de dados sensíveis, como o processamento de dados de cartão.

Com o SDK, é possível:

- Integrar pagamentos com segurança
- Utilizar callbacks para resposta de transações
- Configurar o ambiente de forma flexível, mantendo conformidade com padrões de segurança

O objetivo é fornecer uma base sólida para facilitar a integração, demonstrando boas práticas e fluxos essenciais como autenticação, comandos do cliente e execução de transações de pagamento via **POS Android** ou **Pinpad**.
#

------------

#
## ️⚙️ Configuração de Dependências

O pacote de integração onde esse projeto se encontra contém também a pasta `repository` que é utilizada como um repositório local do maven no arquivo `build.gradle` do projeto. Certifique-se de que a pasta está exatamente no diretório anterior de onde o projeto de exemplo está localizado. Caso contrário, será necessário ajustar a configuração listada abaixo no arquivo `build.gradle` para direcionar as dependências para a pasta correta.
```
   maven {
      url '../../repository'
   }
```
Para desenvolvedores com acesso à intranet da Auttar, é possível obter as dependências através do `artifactory`. Para isso, basta descomentar o trecho abaixo no `build.gradle` do projeto:
```
    maven {
        setUrl("https://artefatosgetnetbr.jfrog.io/artifactory/maven-virtual/")
        credentials {
            username = "${artifactory_user}"
            password = "${artifactory_password}"
        }
    }
```
#

------------
#

## 🧩 Exemplo de Integração com o SDK

Foi desenvolvido um projeto de exemplo demonstrando a integração com o **SDK V3**.

Este exemplo serve como base para desenvolvedores que desejam integrar o SDK em aplicações Android com POS ou Pinpad, seguindo boas práticas de modularização e segurança.

A estrutura do projeto foi organizada em **cinco etapas principais**:
- 1 - Inicialização e Permissões
- 2 - Conectar UI
- 3 - Autenticação do App
- 4 - Solicitar Transação
- 5 - Observar o Resultado da Transação

### 1 - Inicialização e Permissões
Para inicializar o SDK é necessario chamar o método `init` do **objeto**: `br.com.auttar.sdk.Auttar` conforme o ambiente que se deseja utilizar. As opções são:

```kotlin
  Auttar.initHomolog(Application)
  Auttar.initProdution(Application)
  Auttar.initStandAlone(Application)
```

O **objeto** `Auttar` expõe duas classes para definir suas funcionalidades:
- **Auttar.getConfigSDK() : IAuttarConfigSDK** - Permite gerenciar dados do usuário e configurações.
- **Auttar.getSDK() : IAuttarSDK** - Permite acesso às principais funcionalidades do SDK.

Após a inicialização, é recomendado solicitar as permissões necessárias, onde a configuração é feita por meio da **interface** `IAuttarConfigSDK`.

```kotlin
  IAuttarConfigSDK.scanPermissions(Activity)
```

Por exemplo, é possível habilitar o uso de **pinpad USB** como `true` conforme o exemplo:

```kotlin
  IAuttarConfigSDK.userConfiguration.setPinpadUSB(true)
```

### 2 - Conectar UI
O SDK fica responsável por solicitar as telas do fluxo transacional. Para isso, é necessário conectar a interface da aplicação ao SDK antes do inicio da transação conforme o exemplo abaixo:

```kotlin
   IAuttarSDK.connectUI(ClientUserInterface)
```
A **interface** `ClientUserInterface` deve ser implementada pela aplicação e será utilizada pelo SDK sempre que for necessário solicitar interações com o usuário e atulizações de UI.

### 3 - Autenticação do App
A **classe** `IAuttarSDK` possui os métodos necessários para realizar a autenticação conforme o exemplo abaixo:

```kotlin
        IAuttarSDK.authenticationMonoEC(
            ParameterLoginIntegrationMonoEC(
                user = "username",
                password = "password",
                cnpj = "08697957000140", // (exemplo de login em HTI)
                storeCode = null
            ), LoginResultListener)
```
A autenticação responde por meio de uma **callback** com a **interface** `LoginResultInterface`, onde o resultado da autenticação é controlado pela **variável** `code` com valor **zero** no caso sucesso.

### 4 - Solicitar Transação
A **classe** `IAuttarSDK` possui os métodos necessários para realizar a transação. A seguir, será demonstrado o exemplo de um pagamento de crédito.
```kotlin
        IAuttarSDK.paymentTransaction(
            CreditData(
                amount,
                TypeCreditTransaction.CREDITO_GENERICO
            )
        )
```
Os outros tipos de transação são feitos de maneira semelhante, deve-se passar o argumento de acordo com o tipo de transação que se deseja realizar com o **SDK**:
- Pagamento com crédito. (`CreditData`)
- Pagamento com débito. (`DebitData`)
- Cancelamento. (`CancellationData`)


### 5 - Observar o Resultado da Transação
Após um fluxo transacional ser iniciado pela aplicação, o resultado da operação é emitido em um `Flow` que deve ser observado conforme a necessidade do integrador.

```kotlin
  IAuttarSDK.transactionStateFlow
```

O resultado da transação é representado nesse `Flow` pela **sealed interface** `AuttarTransactionState`, onde os possíveis status da operação são:
- **Idle** (Não há transação em progresso)
- **Processing** (Transação em progresso)
- **Done** (Transação completa, contendo o resultado da operação)

Após o final da operação, a **interface** `TefResultInterface` recebe os dados do resultado da transação e pode mostrar dados como **NSU** e **cupom**.

```kotlin
  AuttarTransactionState.Done.result
```
#

------------
#

## 📄 Controle de Versão
#

Histórico de controle de versões da documentação.

| Versão | Data     | Autor         | Descrição                          |
|--------|----------|---------------|-------------------------------------|
| 1.0.0 | 05/09/25 | Danyel Doval  | Versão inicial da documentação.     |
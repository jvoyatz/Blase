/**
 *
 * We could write a use case in this way, in a standard OOP way
 *
 * interface MyUseCase{
 *     suspend operator fun invoke()
 * }
 *
 * class MyUseCaseImpl(): MyUseCase{
 *     override suspend fun invoke(){
 *
 *     }
 * }
 *
 * It is a consistent way of writing a use as well as it is
 * scalable, because if we need to provide more arguments that they are not required to
 * be passed from the View layer, we don't need to change all the points invoking this use case.
 *
 * However, this comes with more code to write and if something changes in our logic,
 * it is required to change everything related to this.
 *
 * Using the Functional way, as written in this package.
 * We write less code, we are consistent and no changes are needed
 * when something is being changed in the repository
 */
package gr.jvoyatz.blase.domain.usecases;
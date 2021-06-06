#include "com_example_ScalarProduct.h"
#include <iostream>
#include <thread>
#include <chrono>

using namespace std;

double scalar_product(JNIEnv* env, int array_length, jobjectArray a_array, jobjectArray b_array, jclass double_class) {

	double product = 0.0;
	jobject a_element, b_element;
	double a_value, b_value;

	//pobranie wartości obiektu typu Double
	jmethodID double_value = env->GetMethodID(double_class, "doubleValue", "()D");

	for (int i = 0; i < array_length; i++) {
		//pobranie elementów z tablicy
		a_element = env->GetObjectArrayElement(a_array, i);
		b_element = env->GetObjectArrayElement(b_array, i);

		//pobranie wartości elementów
		a_value = env->CallDoubleMethod(a_element, double_value);
		b_value = env->CallDoubleMethod(b_element, double_value);

		product += a_value * b_value;
	}

	return product;
}

JNIEXPORT jobject JNICALL Java_com_example_ScalarProduct_multi01 (JNIEnv* env, jobject, jobjectArray a_array, jobjectArray b_array) {
	//pobranie długości tablic
	jsize a_length = env->GetArrayLength(a_array);
	jsize b_length = env->GetArrayLength(b_array);

	//sprawdzenie czy tablice mają tą samą długość
	if (a_length == b_length) {
		//pobranie klasy Double
		jclass double_class = env->FindClass("java/lang/Double");

		//obliczenie iloczynu skalarnego
		double product = scalar_product(env, a_length, a_array, b_array, double_class);

		//pobranie konstruktora klasy Double
		jmethodID double_constructor = env->GetMethodID(double_class, "<init>", "(D)V");

		//stworzenie obiektu klasy Double, który przechowa wynik
		jobject double_object = env->NewObject(double_class, double_constructor, product);

		//usunięcie obiektu
		env->DeleteLocalRef(double_class);

		return double_object;
	}
	
	cout << "Arrays are not the same length.\n";
	//wyczyszczenie bufora
	fflush(stdout);
	return NULL;
}


JNIEXPORT jobject JNICALL Java_com_example_ScalarProduct_multi02 (JNIEnv* env, jobject this_obj, jobjectArray a_array) {
	//pobranie obiektu wywołującego klasę multi02
	jclass scalar_product_class = env->GetObjectClass(this_obj);

	//pobranie id pola b
	jfieldID b_field_id = env->GetFieldID(scalar_product_class, "b", "[Ljava/lang/Double;");

	//pobranie tablicy b
	jobjectArray b_array = (jobjectArray)env->GetObjectField(this_obj, b_field_id);

	//pobranie długości tablic
	jsize a_length = env->GetArrayLength(a_array);
	jsize b_length = env->GetArrayLength(b_array);

	//sprawdzenie czy tablice mają tą samą długość
	if (a_length == b_length)
	{
		//pobranie klasy Double
		jclass double_class = env->FindClass("java/lang/Double");

		//obliczenie iloczynu skalarnego
		double product = scalar_product(env, a_length, a_array, b_array, double_class);

		//pobranie konstruktora klasy Double
		jmethodID double_constructor = env->GetMethodID(double_class, "<init>", "(D)V");

		//stworzenie obiektu klasy Double, który przechowa wynik
		jobject double_object = env->NewObject(double_class, double_constructor, product);

		//usunięcie obiektów
		env->DeleteLocalRef(scalar_product_class);
		env->DeleteLocalRef(b_array);
		env->DeleteLocalRef(double_class);

		return double_object;
	}

	//usunięcie obiektów
	env->DeleteLocalRef(scalar_product_class);
	env->DeleteLocalRef(b_array);
	cout << "Arrays are not the same length.\n";
	//wyczyszczenie bufora
	fflush(stdout);
	return NULL;
}

JNIEXPORT void JNICALL Java_com_example_ScalarProduct_multi03 (JNIEnv* env, jobject this_obj) {
	//pobranie klasy JDialogImpl
	jclass jdialog_impl_class = env->FindClass("Lcom/example/JDialogImpl;");
	//pobranie konstruktora klasy JDialogImpl
	jmethodID jdialog_impl_constructor = env->GetMethodID(jdialog_impl_class, "<init>", "()V");
	//stworzenie obiketu klasy JDialogImpl
	jobject jdialog_impl_object = env->NewObject(jdialog_impl_class, jdialog_impl_constructor);

	//pobranie metody sprawdzającej czy użytkownik zatwierdził wprowadzone wektory
	jmethodID get_was_submit_button_pressed = env->GetMethodID(jdialog_impl_class, "getWasSubmitButtonPressed", "()Z");

	//sprawdzanie w nieskończonej pętli czy użytkownik zatwierdził wprowadzone wektory
	while (env->CallBooleanMethod(jdialog_impl_object, get_was_submit_button_pressed) == JNI_FALSE)
		this_thread::sleep_for(chrono::milliseconds(50));

	//pobranie i wywołanie metod pobierających wektory wprowadzone przez użytkownika
	jmethodID parse_vector_a_input = env->GetMethodID(jdialog_impl_class, "parseVectorAInput", "()[Ljava/lang/Double;");
	jobjectArray vector_a = (jobjectArray)env->CallObjectMethod(jdialog_impl_object, parse_vector_a_input);
	jmethodID parse_vector_b_input = env->GetMethodID(jdialog_impl_class, "parseVectorBInput", "()[Ljava/lang/Double;");
	jobjectArray vector_b = (jobjectArray)env->CallObjectMethod(jdialog_impl_object, parse_vector_b_input);

	//pobranie klasy obiektu na którym wywołano metodę multi03
	jclass scalar_product = env->GetObjectClass(this_obj);

	//pobranie id pola a, b, c w wyżej pobranej klasie
	jfieldID a_field_id = env->GetFieldID(scalar_product, "a", "[Ljava/lang/Double;");
	jfieldID b_field_id = env->GetFieldID(scalar_product, "b", "[Ljava/lang/Double;");
	jfieldID c_field_id = env->GetFieldID(scalar_product, "c", "Ljava/lang/Double;");

	//ustawienie wartości pól a, b
	env->SetObjectField(this_obj, a_field_id, vector_a);
	env->SetObjectField(this_obj, b_field_id, vector_b);

	//pobranie długości tablic
	jsize a_length = env->GetArrayLength(vector_a);
	jsize b_length = env->GetArrayLength(vector_b);

	//sprawdzenie czy tablice mają tą samą długość
	if (a_length == b_length) {
		//pobranie metody multi04
		jmethodID multi04 = env->GetMethodID(scalar_product, "multi04", "()V");
		//wywołanie metody multi04
		env->CallVoidMethod(this_obj, multi04);
	}
	else {
		cout << "Arrays are not the same length.\n";
		//wyczyszczenie bufora
		fflush(stdout);
	}

	//pobranie i wywołanie funkcji zamykającej okno
	jmethodID dispose_id = env->GetMethodID(jdialog_impl_class, "dispose", "()V");
	env->CallVoidMethod(jdialog_impl_object, dispose_id);
	
	//usunięcie obiektów
	env->DeleteLocalRef(jdialog_impl_class);
	env->DeleteLocalRef(jdialog_impl_object);
	env->DeleteLocalRef(vector_a);
	env->DeleteLocalRef(vector_b);
}
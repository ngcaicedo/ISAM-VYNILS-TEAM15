# ISAM-VYNILS-TEAM15

## Dependencias del Proyecto

Este proyecto es una aplicación Android desarrollada en Kotlin. A continuación se listan las principales dependencias utilizadas:

### Dependencias principales:
- **AndroidX Core KTX**: v1.16.0
- **AndroidX AppCompat**: v1.7.0
- **Material Design**: v1.12.0
- **AndroidX Activity**: v1.10.1
- **AndroidX ConstraintLayout**: v2.2.1

### Dependencias de prueba:
- **JUnit**: v4.13.2
- **AndroidX JUnit**: v1.2.1
- **Espresso Core**: v3.6.1

### Configuración del proyecto:
- **Android Gradle Plugin**: v8.9.1
- **Kotlin**: v2.0.21
- **Java Version**: 11
- **Minimum SDK**: 21
- **Target SDK**: 35

## Instrucciones para construir la aplicación de forma local
* Clone el repositorio usando el comando `git clone https://github.com/ngcaicedo/ISAM-VYNILS-TEAM15.git`. Como sugerencia de Android Studio, se recomienda que el proyecto se clone en una ruta sin espacios
   ![image](https://github.com/user-attachments/assets/71f7b174-6529-4cdf-b9ee-52d2350ff5c6)
* Ejecute Android Studio y seleccione la opción `Open`
   ![image](https://github.com/user-attachments/assets/64c24aef-6693-49af-8ce4-b8cb86e11657)
   1. Seleccione la ruta en donde clono el repositorio y de click en el botón `OK`
   ![image](https://github.com/user-attachments/assets/808acc2a-f16c-4165-affe-5f016be3b777)
   2. El proyecto comenzara a sincronizar el Gradle, espere unos minutos a que finalice el proceso
   ![image](https://github.com/user-attachments/assets/cd70f0be-138a-4d7b-81bc-b1022ca8e4c3)
   ![image](https://github.com/user-attachments/assets/26548490-d4d8-4828-8a52-c8ab587763e1)
* Cree el dispositivo virtual para la ejecución del aplicativo. Para esto, diríjase a la opción de `Device Manager` y de click en el ícono `+` o en `Add a new device...` para proseguir con la opción `Create Virtual Device`
   1. ![image](https://github.com/user-attachments/assets/75bf8dab-7c80-4601-b4fd-149bac263f61)
   2. La aplicación está diseñada para ser ejecutada en dispositivos Android con versión Lollipop 5.0 en adelante, por lo cual se recomienda seleccionar la opción `Show obsolete devices profiles` y buscar el dispositivo `Nexus 6` como mínimo, cualquier dispositivo con versiones superiores a la Lollipop 5.0 es compatible. Al finalizar de click en `Next`
![image](https://github.com/user-attachments/assets/3554722f-7898-48ec-aeb6-d5082297549e)
   3. Configure el dispositivo con el nombre deseado, asegurando que la versión API seleccionada sea la `API 21 "Lollipop"; Android 5.0`. Seleccione la imagen de sistema y posteriormente haga click en `Finish`
      ![image](https://github.com/user-attachments/assets/ab0c50cf-f04f-4685-aa68-5adbafc595eb)
   4. Una vez configurado el dispositivo virtual, estará disponible en la barra superior de ejecución y en el `Device Manager`
      ![image](https://github.com/user-attachments/assets/0f4d8c08-9509-4bff-9fe4-314b279a8561)
* Seleccione el botón de ejecución (botón con forma de flecha a la derecha en color verde) para que el aplicativo se compile y se instale en el dispositivo virtual seleccionado
  ![image](https://github.com/user-attachments/assets/e2276e2d-5789-4e9a-b054-7293a59ca191)
   1. El proceso inicia la compilación e instalación en el emulador. Por favor espere a que el proceso termine. Sabra que el proceso termino una vez el aplicativo sea lanzado en el emulador de la parte derecha
      ![image](https://github.com/user-attachments/assets/934de4bb-f11a-47fd-8e47-6b7354381b47)
      ![image](https://github.com/user-attachments/assets/e8156120-1707-4ae8-90fe-c928f7cbf0e5)
Si ha llegado a este punto, felicidades, ha logrado desplegar el aplicativo Android Vynils del Team15 de manera local satisfactoriamente. Ahora, puede proseguir con probar el aplicativo como tenga planeado 


## Ejecución de pruebas con Espresso
Para ejecutar las pruebas de Espresso, asegúrese de que el emulador esté en ejecución y se encuentre con las animaciones desactivadas, para esto puedo ejecutar los siguientes comandos:

```
adb shell settings put global window_animation_scale 0.0
adb shell settings put global transition_animation_scale 0.0
adb shell settings put global animator_duration_scale 0.0
```

Una vez aplique dicha sugerencia, siga estos pasos:

1. Dirijase al path `app/src/androidTest/java/com/example/vynilsapp/`
2. Haga click derecho sobre el archivo `TestVynilsE2EHU01` y luego en Run
3. Espere a que el proceso de ejecución de pruebas termine. Podrá ver los resultados en la parte inferior de Android Studio
4. Si desea ejecutar las pruebas de `TestVynilsE2EHU02`, repita el paso 2, pero esta vez haga click derecho sobre el archivo `TestVynilsE2EHU02` y luego en Run
5. Espere a que el proceso de ejecución de pruebas termine. Podrá ver los resultados en la parte inferior de Android Studio
6. Haga click derecho sobre el archivo `TestVynilsE2EHU03` y luego en Run 
7. Espere a que el proceso de ejecución de pruebas termine. Podrá ver los resultados en la parte inferior de Android Studio 
8. Haga click derecho sobre el archivo `TestVynilsE2EHU04` y luego en Run 
9. Espere a que el proceso de ejecución de pruebas termine. Podrá ver los resultados en la parte inferior de Android Studio 
10. Haga click derecho sobre el archivo `TestVynilsE2EHU05` y luego en Run 
11. Espere a que el proceso de ejecución de pruebas termine. Podrá ver los resultados en la parte inferior de Android Studio 
12. Haga click derecho sobre el archivo `TestVynilsE2EHU06` y luego en Run
13. Espere a que el proceso de ejecución de pruebas termine. Podrá ver los resultados en la parte inferior de Android Studio
14. Haga click derecho sobre el archivo `TestVynilsE2EHU07` y luego en Run 
15. Espere a que el proceso de ejecución de pruebas termine. Podrá ver los resultados en la parte inferior de Android Studio
16. Haga click derecho sobre el archivo `TestVynilsE2EHU08` y luego en Run
17. Espere a que el proceso de ejecución de pruebas termine. Podrá ver los resultados en la parte inferior de Android Studio

### Nota:
```
- Se pueden presentar errores al ejecutar las pruebas de `TestVynilsE2EHU02` debido a los tiempos de respuesta de la API (La cual se encuentra desplegada en una arquitectura de capa gratuita AWS), para solventar esto, se recomienda aumentar el tiempo de espera en el `Thread.sleep` ubicado en la línea 94.
- Para ejecutar las pruebas en multi-idiomas, se recomienda cambiar el idioma del emulador, para esto, diríjase a la configuración del emulador y cambie el idioma. Luego, ejecute las pruebas nuevamente. Los idiomas soportados son Alemán, Francés, Español e Ingles
```

## Ubicación del APK
El APK generado se encuentra en la ruta raiz del proyecto en la carpeta `APK\1.2.0`, el cual puede descargar e instalar en su dispositivo Android. Para instalar el APK, asegúrese de habilitar la opción de `Instalación de aplicaciones de fuentes desconocidas` en la configuración de su dispositivo. La aplicación es compatible con dispositivos Android 5.0 (Lollipop) y versiones superiores.
 

  




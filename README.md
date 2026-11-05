# study-for-lazy

Трекер посещаемости учёбы с карательным будильником и подтверждением пар через фото конспектов (Kotlin + Compose).

## Статус

Каркас: Material 3, тёмная тема по умолчанию, нижняя навигация на три экрана-заглушки
(«Расписание», «Будильник-садист», «Статистика проёбов»).

## Сборка и запуск

```bash
./gradlew assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

Нужны JDK 17+ и Android SDK: `platforms;android-34`, `build-tools;34.0.0`, `platform-tools`.
Путь к SDK задаётся в `local.properties` (файл в git не входит):

```properties
sdk.dir=/home/<user>/Android/Sdk
```

Открыть в Android Studio: `File → Open` → папка проекта.

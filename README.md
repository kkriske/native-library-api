## Setup

### Project layout

#### [library](./library)

* GraalVM compiled shared library.
* Test suite is compiled to native image as well.

#### [wrapper-java](./wrapper-java)

* Java wrapper around the `library` module, using `FFI` (released in JDK 23).
* Test suite is compiled to native image as well.

#### [wrapper-python](./wrapper-python)

* Python wrapper around the `library` module, using `ctypes` (built-in lib in python).
* Alternative implementation using `cffi` is still pending.

### IntelliJ configuration

Import the project as a standard Maven project.
To property support the [wrapper-python](wrapper-python) module, add a Python Facet to the `File > Project Structure`
settings.
Add a new Python Interpreter, select an existing interpreter and point it
to [wrapper-python/.venv/Scripts/python.exe](wrapper-python/.venv/Scripts/python.exe)

### Security

For security and tamper resistance in the native library, SHA-256 hashing should be used:

* After building the native library:
  * create a sibling file containing the SHA-256 hash
  * create a sibling file containing the filesize of the library (does this provide added value over the hash?)
* Never let the OS figure out what library to load:
    * locate the library ourselves in the expected locations, using `System.load` instead of `System.loadLibrary`,
      `java.library.path`, `LD_LIBRARY_PATH` or other system dependant ways to locate dependencies makes it easier to
      override the library that will be loaded.
    * validate the SHA-256 hash before loading the absolute file reference

### Library Locations

| Runtime                             | Location                                                           |
|-------------------------------------|--------------------------------------------------------------------|
| Python \| Java \| native-image      | `<os-temp-dir>/library-<SHA-256>/(lib)library.{so\|dll\|dylib}`    |
| native-image exported<sup>[1]</sup> | `<executable-dir>/(lib)library.{so\|dll\|dylib}`                   |
| Cython                              | ? - Unclear what the exact implementation specifics of Cython are. |

[1]: at build-time the `io.github.kkriske.wrapper.exportPath` property can be set to export the os-specific library.
Using this flag will no longer embed the library in the final executable, and it is assumed to be packaged next to the
executable itself. SHA-256 will still be validated.

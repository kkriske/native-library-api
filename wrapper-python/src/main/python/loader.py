from ctypes import cdll
from platform import system


def function_factory(function,
                     argument_types=None,
                     return_type=None,
                     error_checking=None):
    if argument_types is not None:
        function.argtypes = argument_types
    function.restype = return_type
    if error_checking is not None:
        function.errcheck = error_checking
    return function


match system():
    case 'Linux':
        __libname = 'library.so'
    case 'Windows':
        __libname = 'library.dll'
    case 'Darwin':
        __libname = 'library.dylib'

lib = cdll.LoadLibrary("../../../target/generated-sources/resources/io/github/kkriske/library/native/" + __libname)

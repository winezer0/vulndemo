def parameter_sink(first, second=None, third=None): pass
def plain_parameters(self, cls, _):
    parameter_sink(self, cls, _)
class ParameterService:
    @staticmethod
    def static(self, cls):
        parameter_sink(self, cls)
    def instance(receiver, value):
        parameter_sink(value)
    @classmethod
    def factory(owner, value):
        parameter_sink(value)

static_alias = staticmethod
class ParameterAliasService:
    @static_alias
    def alias(self, cls):
        parameter_sink(self, cls)

def duplicate(first, second):
    parameter_sink(first, first=second)
class_alias = classmethod
class ClassmethodAliasService:
    @class_alias
    def from_alias(owner, value):
        parameter_sink(value)
def use_class_alias(value):
    ClassmethodAliasService.from_alias(value)

from builtins import classmethod as builtin_classmethod

class BuiltinImportAliasService:
    @builtin_classmethod
    def from_builtin_alias(owner, value):
        parameter_sink(value)

def use_builtin_class_alias(value):
    BuiltinImportAliasService.from_builtin_alias(value)

chained_classmethod = builtin_classmethod
class ChainedBuiltinImportAliasService:
    @chained_classmethod
    def from_chained_builtin_alias(owner, value):
        parameter_sink(value)

def use_chained_builtin_alias(value):
    ChainedBuiltinImportAliasService.from_chained_builtin_alias(value)

if runtime_flag:
    branch_class_alias = classmethod
else:
    branch_class_alias = classmethod
class BranchClassmethodAliasService:
    @branch_class_alias
    def from_branch_alias(owner, value):
        parameter_sink(value)
def use_branch_class_alias(value):
    BranchClassmethodAliasService.from_branch_alias(value)

class_alias = classmethod
try:
    raise RuntimeError()
except RuntimeError as class_alias:
    pass
class ExceptionClassmethodAliasService:
    @class_alias
    def from_exception_alias(owner, value):
        parameter_sink(value)

class_alias = classmethod
with context as class_alias:
    pass
class WithTargetAliasService:
    @class_alias
    def from_with_alias(owner, value):
        parameter_sink(value)
def use_with_alias(value):
    WithTargetAliasService.from_with_alias(value)

class_alias = classmethod
type class_alias = object
class TypeAliasTargetService:
    @class_alias
    def from_type_alias(owner, value):
        parameter_sink(value)
def use_type_alias(value):
    TypeAliasTargetService.from_type_alias(value)

class_alias = classmethod
(class_alias := custom)
class WalrusTargetAliasService:
    @class_alias
    def from_walrus_alias(owner, value):
        parameter_sink(value)
def use_walrus_alias(value):
    WalrusTargetAliasService.from_walrus_alias(value)

class_alias = classmethod
match subject:
    case class_alias:
        pass
class MatchCaptureAliasService:
    @class_alias
    def from_match_capture(owner, value):
        parameter_sink(value)
def use_match_capture_alias(value):
    MatchCaptureAliasService.from_match_capture(value)

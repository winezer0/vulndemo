class ExternalBox { int x; void send() {} private void hidden() {} }
class ExternalHolder { ExternalBox box; }
class ExternalSupplier { ExternalHolder makeHolder() { return new ExternalHolder(); } }

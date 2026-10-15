class RefBox { public: int value; };
class RefFactory { public: volatile const RefBox& make(); int (*callback)(int); };
volatile const RefBox& RefFactory::make() { static RefBox box; return box; }
int readReference() { RefFactory factory; return factory.make().value; }

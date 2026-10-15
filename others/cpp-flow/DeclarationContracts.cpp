class QtCommentOnly { /* Q_OBJECT */ public: int value; };
class QtStringOnly { public: const char* text = "Q_OBJECT"; };
class QtOwnMarker { Q_OBJECT public: int value; };

template<int *Pointer> struct PointerTemplate {};
struct UnnamedItem {};
void unnamedSlots(int, const UnnamedItem&, int value) {}

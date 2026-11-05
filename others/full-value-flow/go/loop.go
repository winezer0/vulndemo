package fullvalueflow

// loop_field consumes a value written by the preceding iteration.
func loop_field(loop_fieldInput string) string { holder := Box{}; value := ""; for i := 0; i < 2; i++ { value = holder.Value; holder.Value = loop_fieldInput }; loop_fieldOut := value; return loop_fieldOut }

// loop_key retains a prior iteration's same-key element value.
func loop_key(loop_keyInput string) string { holder := map[string]string{}; value := ""; for i := 0; i < 2; i++ { value = holder["same"]; holder["same"] = loop_keyInput }; loop_keyOut := value; return loop_keyOut }

// loop_distinct preserves field separation across iterations.
func loop_distinct(loop_distinctInput string) string { holder := struct{Value, Other string}{}; value := ""; for i := 0; i < 2; i++ { value = holder.Other; holder.Value = loop_distinctInput }; loop_distinctOut := value; return loop_distinctOut }

// loop_after cannot consume a value assigned after the loop finishes.
func loop_after(loop_afterInput string) string { holder := Box{}; value := ""; for i := 0; i < 2; i++ { value = holder.Value }; holder.Value = loop_afterInput; loop_afterOut := value; return loop_afterOut }

// loop_nested propagates through actual repeated nested bodies without assuming a fixed execution order.
func loop_nested(loop_nestedInput string) string { holder := Box{}; value := ""; for i := 0; i < 2; i++ { for j := 0; j < 2; j++ { value = holder.Value; holder.Value = loop_nestedInput } }; loop_nestedOut := value; return loop_nestedOut }

// loop_chain needs several runtime iterations but only the finite source operation graph.
func loop_chain(loop_chainInput string) string { first := Box{}; second := Box{}; value := ""; for i := 0; i < 3; i++ { value = first.Value; first.Value = second.Value; second.Value = loop_chainInput }; loop_chainOut := value; return loop_chainOut }

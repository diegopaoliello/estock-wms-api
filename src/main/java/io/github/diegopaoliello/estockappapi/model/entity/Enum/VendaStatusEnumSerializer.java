package io.github.diegopaoliello.estockappapi.model.entity.Enum;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class VendaStatusEnumSerializer extends StdSerializer<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public VendaStatusEnumSerializer() {
		super(VendaStatusEnumSerializer.class, false);
	}

	@Override
	public void serialize(Object value, JsonGenerator generator, SerializerProvider provider) throws IOException {
		VendaStatusEnum status = (VendaStatusEnum) value;

		generator.writeStartObject();
		generator.writeFieldName("name");
		generator.writeString(status.name());
		generator.writeFieldName("descricao");
		generator.writeNumber(status.getId());
		generator.writeEndObject();
	}
}
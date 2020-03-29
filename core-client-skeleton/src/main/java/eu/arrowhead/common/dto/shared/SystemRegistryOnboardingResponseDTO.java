package eu.arrowhead.common.dto.shared;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.Map;
import java.util.StringJoiner;

@JsonInclude(Include.NON_NULL)
public abstract class SystemRegistryOnboardingResponseDTO extends SystemRegistryResponseDTO implements Serializable {

    //=================================================================================================
    // members

    private static final long serialVersionUID = -635438605292398404L;
    private CertificateCreationResponseDTO certificateResponse;

    //=================================================================================================
    // methods

    public SystemRegistryOnboardingResponseDTO() {
    }

    public SystemRegistryOnboardingResponseDTO(final long id, final SystemResponseDTO system,
                                               final DeviceResponseDTO provider, final String endOfValidity,
                                               final Map<String, String> metadata, final int version,
                                               final String createdAt, final String updatedAt,
                                               final CertificateCreationResponseDTO certificateResponse) {
        super(id, system, provider, endOfValidity, metadata, version, createdAt, updatedAt);
        this.certificateResponse = certificateResponse;
    }

    //-------------------------------------------------------------------------------------------------
    public CertificateCreationResponseDTO getCertificateResponse() {
        return certificateResponse;
    }

    public void setCertificateResponse(final CertificateCreationResponseDTO certificateResponse) {
        this.certificateResponse = certificateResponse;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SystemRegistryOnboardingResponseDTO.class.getSimpleName() + "[", "]")
            .add("certificateResponse=" + certificateResponse).add("parent=" + super.toString()).toString();
    }
}
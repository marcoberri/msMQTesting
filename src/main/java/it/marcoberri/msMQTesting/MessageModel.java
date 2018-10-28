package it.marcoberri.msMQTesting;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageModel implements Serializable {
	private static final long serialVersionUID = -7322659695504390238L;
	private String message;
	private String fromServer;
	private String fromApplication;
	private String port;
}
//package com.fushun.framework.web.config.filter;
//
//import jakarta.servlet.ServletOutputStream;
//import jakarta.servlet.WriteListener;
//import org.apache.commons.io.output.TeeOutputStream;
//
//import java.io.IOException;
//import java.io.OutputStream;
//
///**
// * Created by liuxd on 2020/06/20.
// */
//public class TeeServletOutputStream extends ServletOutputStream {
//
//    private final TeeOutputStream teeOutputStream;
//
//    public TeeServletOutputStream(OutputStream one, OutputStream two) {
//        this.teeOutputStream = new TeeOutputStream(one, two);
//    }
//
//    @Override
//    public void write(byte[] b) throws IOException {
//        this.teeOutputStream.write(b);
//    }
//
//    @Override
//    public void write(byte[] b, int off, int len) throws IOException {
//        this.teeOutputStream.write(b, off, len);
//    }
//
//    @Override
//    public void write(int b) throws IOException {
//        this.teeOutputStream.write(b);
//    }
//
//    @Override
//    public void flush() throws IOException {
//        super.flush();
//        this.teeOutputStream.flush();
//    }
//
//    @Override
//    public void close() throws IOException {
//        super.close();
//        this.teeOutputStream.close();
//    }
//
//    @Override
//    public boolean isReady() {
//        return false;
//    }
//
//    @Override
//    public void setWriteListener(WriteListener writeListener) {
//
//    }
//}
